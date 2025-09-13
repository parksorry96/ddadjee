SHELL := /bin/bash
.DEFAULT_GOAL := help

# Load environment (for psql, ports, etc.)
-include .env
export

DOCKER ?= docker
COMPOSE ?= docker compose

SERVICES_BASE := postgres redis kafka
SERVICES_TOOLS := kafdrop
SERVICES_ELK := elasticsearch kibana

# Defaults if .env missing
POSTGRES_USER ?= ddadjee
POSTGRES_PASSWORD ?= ddadjee
POSTGRES_DB ?= ddadjee

## Show available commands
help:
	@echo "Available make targets:" && \
	grep -E '^[a-zA-Z0-9_.-]+:.*##' Makefile | sort | awk -F':|##' '{printf "  %-24s %s\n", $$1, $$NF}'

## Create .env from example (no overwrite)
env-init:
	@[ -f .env ] && echo ".env exists" || (cp .env.example .env && echo "Created .env from .env.example")

# --------------------
# Infra (Docker Compose)
# --------------------
up: ## Start base infra (postgres, redis, kafka)
	$(COMPOSE) up -d $(SERVICES_BASE)

up-tools: ## Start tools profile (kafdrop)
	$(COMPOSE) --profile tools up -d $(SERVICES_TOOLS)

up-elk: ## Start ELK profile (elasticsearch, kibana)
	$(COMPOSE) --profile elk up -d $(SERVICES_ELK)

up-all: ## Start all services incl. tools and elk
	$(COMPOSE) --profile tools --profile elk up -d

down: ## Stop and remove containers (volumes preserved)
	$(COMPOSE) down

down-v: ## Stop and remove containers + volumes (DANGEROUS)
	$(COMPOSE) down -v

ps: ## Show compose services
	$(COMPOSE) ps

logs: ## Tail logs (usage: make logs svc=postgres)
	@[ "$(svc)" != "" ] || (echo "Usage: make logs svc=<service>" && exit 2)
	$(COMPOSE) logs -f $(svc)

restart: ## Restart a service (usage: make restart svc=postgres)
	@[ "$(svc)" != "" ] || (echo "Usage: make restart svc=<service>" && exit 2)
	$(COMPOSE) restart $(svc)

# --------------------
# Datastores quick shells
# --------------------
psql: ## Open psql shell to Postgres container
	$(DOCKER) exec -it ddj-postgres psql -U $(POSTGRES_USER) -d $(POSTGRES_DB)

redis: ## Open redis-cli shell to Redis container
	$(DOCKER) exec -it ddj-redis redis-cli

# --------------------
# Kafka helpers (single-node KRaft via bitnami)
# --------------------
PARTITIONS ?= 1
RF ?= 1

kafka-topics: ## List topics
	$(DOCKER) exec -it ddj-kafka /opt/bitnami/kafka/bin/kafka-topics.sh --bootstrap-server kafka:29092 --list

kafka-topic-create: ## Create topic (usage: make kafka-topic-create topic=name [PARTITIONS=1 RF=1])
	@[ "$(topic)" != "" ] || (echo "Usage: make kafka-topic-create topic=<name> [PARTITIONS=1 RF=1]" && exit 2)
	$(DOCKER) exec -it ddj-kafka /opt/bitnami/kafka/bin/kafka-topics.sh --bootstrap-server kafka:29092 --create --if-not-exists --topic $(topic) --partitions $(PARTITIONS) --replication-factor $(RF)

kafka-produce: ## Console producer (usage: make kafka-produce topic=name)
	@[ "$(topic)" != "" ] || (echo "Usage: make kafka-produce topic=<name>" && exit 2)
	$(DOCKER) exec -it ddj-kafka /opt/bitnami/kafka/bin/kafka-console-producer.sh --bootstrap-server kafka:29092 --topic $(topic)

kafka-consume: ## Console consumer from beginning (usage: make kafka-consume topic=name)
	@[ "$(topic)" != "" ] || (echo "Usage: make kafka-consume topic=<name>" && exit 2)
	$(DOCKER) exec -it ddj-kafka /opt/bitnami/kafka/bin/kafka-console-consumer.sh --bootstrap-server kafka:29092 --topic $(topic) --from-beginning

# --------------------
# Backend / Frontend shortcuts
# --------------------
backend-run: ## Run Spring Boot locally
	cd backend && ./gradlew bootRun

backend-test: ## Run backend tests
	cd backend && ./gradlew test

backend-build: ## Build backend JAR
	cd backend && ./gradlew build

frontend-dev: ## Start Vite dev server
	cd frontend && npm i && npm run dev

frontend-build: ## Build frontend
	cd frontend && npm i && npm run build

frontend-lint: ## Lint frontend
	cd frontend && npm i && npm run lint

.PHONY: help env-init up up-tools up-elk up-all down down-v ps logs restart psql redis \
        kafka-topics kafka-topic-create kafka-produce kafka-consume \
        backend-run backend-test backend-build frontend-dev frontend-build frontend-lint

