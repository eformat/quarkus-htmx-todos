# Todo App in Quarkus with htmx

This is a demo application showing the use of [htmx](https://htmx.org/) with a Quarkus backend.

There is also a [Hotwire](https://hotwire.dev/) version for comparison: https://github.com/derkoe/quarkus-hotwire-todos

Run postgres
```bash
podman-compose up -d
```

Run the app locally
```bash
mvn quarkus:dev
```

## SQLite version

See `src/main/resources/application.properties`

Demo litestream s3 realtime backups
- https://fly.io/blog/all-in-on-sqlite-litestream/

```bash
podman run -p 9000:9000 -p 9001:9001 minio/minio server /data --console-address ":9001"
```

Replicate to s3

```bash
export LITESTREAM_ACCESS_KEY_ID=minioadmin
export LITESTREAM_SECRET_ACCESS_KEY=minioadmin
litestream replicate todos.db s3://todos.localhost:9000/todos.db
```

Restore from s3

```bash
export LITESTREAM_ACCESS_KEY_ID=minioadmin
export LITESTREAM_SECRET_ACCESS_KEY=minioadmin
litestream restore -o todos2.db s3://todos.localhost:9000/todos.db
sqlite3 todos2.db 'SELECT * FROM todos'
```
