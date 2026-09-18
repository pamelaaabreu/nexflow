# Architecture decisions

## Why a modular monolith?
NexFlow intentionally uses a modular monolith instead of microservices for the first production-ready portfolio release. The business domains are separated in code while deployment remains simple.

## Domain boundaries
- `auth`: identity and access
- `product`: product catalog
- `inventory`: stock and movement rules
- `order`: ordering and fulfillment state machine
- `dashboard`: read-oriented operational KPIs
- `audit`: business activity trace

## Key business invariants
1. Available stock = physical stock - reserved stock.
2. Creating an order reserves inventory atomically.
3. Cancelling an unshipped order releases its reservations.
4. Shipping consumes both physical and reserved quantities.
5. Invalid order status transitions are rejected.
6. Important writes generate audit events.

## Next evolution
The inventory/order boundary can later emit domain events through RabbitMQ. The dashboard can consume projections and Redis cache. SSE/WebSocket can notify the Angular application about operational updates.
