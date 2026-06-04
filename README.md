# ipfix-generator

![CI](https://github.com/JFWenisch/ipfix-generator/actions/workflows/ci.yml/badge.svg)
![Release](https://img.shields.io/github/v/release/JFWenisch/ipfix-generator)
![License](https://img.shields.io/github/license/JFWenisch/ipfix-generator)
![Artifact Hub](https://img.shields.io/endpoint?url=https://artifacthub.io/badge/repository/jfwenisch)

`ipfix-generator` is a Spring Boot application for generating IPFIX traffic for demos, lab environments, and integration testing. It provides a browser-based UI for creating generator jobs and a small REST API for listing jobs and their history.

## Features

- Web UI for creating and reviewing IPFIX generation jobs
- Supports both `L2IP` and `IPv4 5-tuple` generator templates
- REST endpoints for listing jobs and retrieving per-job history
- Hosted Swagger UI for interactive REST API documentation
- Configurable target host, port, packets-per-second, and packet counts
- Container image published to GitHub Container Registry
- Helm chart for Kubernetes-based deployments

## Screenshots

<p align="center">
  <img src="https://raw.githubusercontent.com/JFWenisch/ipfix-generator/main/docs/img/preview_home.jpeg" alt="IPFIX Generator home screen" width="32%">
  <img src="https://raw.githubusercontent.com/JFWenisch/ipfix-generator/main/docs/img/preview_jobs.jpeg" alt="IPFIX Generator jobs overview" width="32%">
  <img src="https://raw.githubusercontent.com/JFWenisch/ipfix-generator/main/docs/img/preview_jobdetails.jpeg" alt="IPFIX Generator job details" width="32%">
</p>


## Quickstart with Docker

1. Start the container:

```bash
docker run -d \
  --name ipfix-generator \
  -p 8080:8080 \
  ghcr.io/jfwenisch/ipfix-generator:latest
```

2. Open `http://localhost:8080` in your browser.

3. Create a generator job from the home page by setting:

- destination host
- destination port
- packets per second
- total packets

4. Review active jobs under `http://localhost:8080/jobs`.

5. Inspect job history in the UI or through `GET /api/jobs/{id}/history`.

## Helm Chart

### Install from the chart repository

```bash
helm repo add jfwenisch https://charts.wenisch.tech
helm repo update
helm install ipfix-generator jfwenisch/ipfix-generator -n ipfix-generator --create-namespace
```

### Install from this repository

```bash
helm install ipfix-generator ./chart -n ipfix-generator --create-namespace
```

### Upgrade an existing release

```bash
helm upgrade ipfix-generator ./chart -n ipfix-generator
```


Chart-specific configuration examples are documented in [`chart/README.md`](chart/README.md).

## Application Usage

### Web UI

- `GET /` renders the landing page and job creation form
- `GET /jobs` renders the jobs overview page
- `GET /jobs/{id}` renders the job detail view

### REST API

- `GET /api/jobs` returns all known generator jobs
- `GET /api/jobs/{id}` returns one generator job
- `GET /api/jobs/{id}/history` returns the history of one job
- `POST /api/jobs` creates a new generator job from a JSON request body
- `template` supports `L2IP` and `IPV4_FIVE_TUPLE`, defaulting to `L2IP` when omitted
- `POST /api/jobs/{id}/stop` stops a running or continuous generator job
- `GET /v3/api-docs` exposes the OpenAPI document
- `GET /swagger-ui/index.html` serves the interactive Swagger UI

The application keeps job state in memory, so a restart clears the current job list.

Example create request:

```json
{
  "destHost": "127.0.0.1",
  "destPort": "4739",
  "pps": "1",
  "totalPackets": "10",
  "template": "IPV4_FIVE_TUPLE"
}
```

## Build and Development

### Prerequisites

- Java 21
- Maven 3.9+
- Docker
- Helm 3.x

### Build and Test

```bash
mvn -B -f src/pom.xml test
mvn -B -f src/pom.xml package
```

### Run from Source

```bash
mvn -B -f src/pom.xml spring-boot:run
```

### Build the Container Locally

```bash
docker build -t ghcr.io/jfwenisch/ipfix-generator:local .
docker run --rm -p 8080:8080 ghcr.io/jfwenisch/ipfix-generator:local
```


## License

This project is licensed under the GNU GPL v3. See [LICENSE](LICENSE) for details.

## Contributing

Contributions are welcome. Fork the repository, make your changes in a feature branch, and open a pull request with a clear description of the change.

## Acknowledgments

This project is influenced by [jFlowLib](https://github.com/DE-CIX/jFlowLib/tree/master), which provides a Java library to parse and generate sFlow and IPFIX data.
