CREATE TABLE app_users (
    id UUID PRIMARY KEY,
    name VARCHAR(120) NOT NULL,
    email VARCHAR(180) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(30) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE products (
    id UUID PRIMARY KEY,
    name VARCHAR(180) NOT NULL,
    sku VARCHAR(80) NOT NULL UNIQUE,
    category VARCHAR(100) NOT NULL,
    price NUMERIC(14,2) NOT NULL,
    stock_quantity INTEGER NOT NULL DEFAULT 0,
    reserved_quantity INTEGER NOT NULL DEFAULT 0,
    min_stock INTEGER NOT NULL DEFAULT 5,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE orders (
    id UUID PRIMARY KEY,
    code VARCHAR(40) NOT NULL UNIQUE,
    customer_name VARCHAR(150) NOT NULL,
    customer_email VARCHAR(180) NOT NULL,
    total NUMERIC(14,2) NOT NULL,
    status VARCHAR(40) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE order_items (
    id UUID PRIMARY KEY,
    order_id UUID NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
    product_id UUID NOT NULL,
    product_name VARCHAR(180) NOT NULL,
    quantity INTEGER NOT NULL,
    unit_price NUMERIC(14,2) NOT NULL,
    total NUMERIC(14,2) NOT NULL
);

CREATE TABLE inventory_movements (
    id UUID PRIMARY KEY,
    product_id UUID NOT NULL REFERENCES products(id),
    movement_type VARCHAR(40) NOT NULL,
    quantity INTEGER NOT NULL,
    reference VARCHAR(120),
    created_by VARCHAR(180) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE audit_events (
    id UUID PRIMARY KEY,
    user_email VARCHAR(180) NOT NULL,
    action VARCHAR(80) NOT NULL,
    entity_name VARCHAR(80) NOT NULL,
    entity_id VARCHAR(80),
    description VARCHAR(500) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX idx_orders_status ON orders(status);
CREATE INDEX idx_orders_created_at ON orders(created_at DESC);
CREATE INDEX idx_inventory_product ON inventory_movements(product_id);
CREATE INDEX idx_audit_created_at ON audit_events(created_at DESC);
