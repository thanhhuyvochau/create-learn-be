#!/bin/sh

echo "⏳ Waiting for MinIO to start..."
sleep 10

echo "🏗️ Configuring MinIO client..."
mc alias set local http://minio:9000 minioadmin minioadmin123

echo "🏗️ Ensuring bucket 'create-learn-storage' exists..."
if ! mc ls local/create-learn-storage >/dev/null 2>&1; then
  mc mb local/create-learn-storage
  echo "✅ Bucket created."
else
  echo "ℹ️ Bucket already exists."
fi

echo "📤 Syncing data from /data/minio/* into bucket (overwrite enabled)..."
mc mirror --overwrite /data/minio local/create-learn-storage

echo "🎉 All objects updated successfully."
