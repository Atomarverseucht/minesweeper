FROM eclipse-temurin:21-jdk

ENV SCALA_VERSION=3.7.3
ENV SBT_VERSION=1.10.5

# Install required libraries for JavaFX/ScalaFX GUI
RUN apt-get update && apt-get install -y \
    libgl1-mesa-glx \
    libgtk-3-0 \
    libxrender1 \
    libxtst6 \
    libxi6 \
    libxrandr2 \
    libxcursor1 \
    libxinerama1 \
    && rm -rf /var/lib/apt/lists/*

# Install Coursier and Scala toolchain
RUN curl -fL https://github.com/coursier/coursier/releases/latest/download/cs-x86_64-pc-linux.gz \
    | gzip -d > /usr/local/bin/cs && \
    chmod +x /usr/local/bin/cs && \
    cs setup --yes --jvm 21 && \
    cs install scala:${SCALA_VERSION} scalac:${SCALA_VERSION} sbt:${SBT_VERSION}

ENV PATH="/root/.local/share/coursier/bin:${PATH}"

WORKDIR /winesmeeper
COPY . /winesmeeper

# Compile the project
RUN sbt compile

# Run the application
CMD ["sbt", "run"]