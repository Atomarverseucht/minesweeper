FROM eclipse-temurin:21-jdk as builder

ENV SCALA_VERSION=3.7.3
ENV SBT_VERSION=1.10.5

# Install Coursier and Scala toolchain
RUN curl -fL https://github.com/coursier/coursier/releases/latest/download/cs-x86_64-pc-linux.gz \
    | gzip -d > /usr/local/bin/cs && \
    chmod +x /usr/local/bin/cs && \
    cs setup --yes --jvm 21 && \
    cs install sbt:${SBT_VERSION}

ENV PATH="/root/.local/share/coursier/bin:${PATH}"

WORKDIR /build
COPY build.sbt project/ ./
COPY project ./project

# Download dependencies only (cached layer)
RUN sbt update

# Copy source and compile
COPY src ./src
RUN sbt compile && \
    rm -rf /root/.sbt/boot/* /root/.ivy2/cache/* /root/.cache/coursier/*

# Runtime stage - much smaller
FROM eclipse-temurin:21-jdk

# Install only required GUI libraries
RUN apt-get update && apt-get install -y \
    libgl1 \
    libgtk-3-0 \
    && rm -rf /var/lib/apt/lists/*

# Install minimal sbt
RUN curl -fL https://github.com/coursier/coursier/releases/latest/download/cs-x86_64-pc-linux.gz \
    | gzip -d > /usr/local/bin/cs && \
    chmod +x /usr/local/bin/cs && \
    cs install sbt:1.10.5

ENV PATH="/root/.local/share/coursier/bin:${PATH}"

WORKDIR /winesmeeper
COPY --from=builder /build ./

CMD ["sbt", "run"]