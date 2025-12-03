import os

# Define file path and size
file_path = 'C:/Users/Rain/Desktop/largefile.txt'
size_mb = 1  # Size in megabytes

# Generate a large file filled with zero bytes
with open(file_path, 'wb') as f:
    chunk = b'\0' * 1024 * 1024  # 1 MB chunk
    for _ in range(size_mb):
        f.write(chunk)