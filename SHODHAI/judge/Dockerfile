# Use OpenJDK 17 as base image
FROM openjdk:17-jdk-slim

# Install necessary tools
RUN apt-get update && apt-get install -y \
    coreutils \
    && rm -rf /var/lib/apt/lists/*

# Create workspace directory
WORKDIR /workspace

# Set up security restrictions
RUN groupadd -r judge && useradd -r -g judge judge
RUN chown -R judge:judge /workspace

# Switch to non-root user
USER judge

# Default command
CMD ["bash"]

