# ipfix-generator Helm Chart

The `ipfix-generator` Helm chart deploys the Spring Boot IPFIX generator UI and API to Kubernetes.

## Features

- Stateless deployment by default
- Configurable image, resource requests, and probe settings
- Optional ingress support
- Generic `env` and `secrets` pass-through for runtime configuration
- Compatible with the published image at `ghcr.io/jfwenisch/ipfix-generator`

## Prerequisites

- Kubernetes 1.24+
- Helm 3.x

## Installation

### Install from the chart repository

```bash
helm repo add jfwenisch https://charts.wenisch.tech
helm repo update
helm install ipfix-generator jfwenisch/ipfix-generator -n ipfix-generator --create-namespace
```

### Install from source

```bash
helm install ipfix-generator ./chart -n ipfix-generator --create-namespace
```

### Upgrade an existing release

```bash
helm upgrade ipfix-generator ./chart -n ipfix-generator
```

### Uninstall

```bash
helm uninstall ipfix-generator -n ipfix-generator
```

## Configuration

All chart values are optional. The default deployment is intentionally simple and stateless.

### Basic examples

Use a custom image tag:

```bash
helm install ipfix-generator ./chart \
  -n ipfix-generator --create-namespace \
  --set image.tag=0.4.2
```

Set Java runtime options:

```bash
helm install ipfix-generator ./chart \
  -n ipfix-generator --create-namespace \
  --set env.JAVA_TOOL_OPTIONS="-Xms256m -Xmx512m"
```

Provide a secret-backed environment variable:

```bash
helm install ipfix-generator ./chart \
  -n ipfix-generator --create-namespace \
  --set secrets.EXAMPLE_TOKEN="change-me"
```

Enable ingress:

```bash
helm install ipfix-generator ./chart \
  -n ipfix-generator --create-namespace \
  --set ingress.enabled=true \
  --set ingress.className=nginx \
  --set ingress.hosts[0].host=ipfix.example.com \
  --set ingress.hosts[0].paths[0].path=/
```

## Key values

| Parameter | Default | Description |
|---|---|---|
| `replicaCount` | `1` | Number of pods when autoscaling is disabled |
| `image.repository` | `ghcr.io/jfwenisch/ipfix-generator` | Container image repository |
| `image.tag` | `""` | Image tag override, defaults to chart `appVersion` |
| `service.type` | `ClusterIP` | Kubernetes service type |
| `service.port` | `8080` | Service port |
| `service.targetPort` | `8080` | Container port exposed by the service |
| `env` | `{}` | Non-sensitive environment variables |
| `secrets` | `{}` | Sensitive environment variables rendered into a `Secret` |
| `resources.requests.cpu` | `100m` | CPU request |
| `resources.requests.memory` | `256Mi` | Memory request |
| `resources.limits.cpu` | `500m` | CPU limit |
| `resources.limits.memory` | `512Mi` | Memory limit |
| `ingress.enabled` | `false` | Enables ingress creation |
| `autoscaling.enabled` | `false` | Enables HPA creation |

See [`values.yaml`](values.yaml) and [`values.schema.json`](values.schema.json) for the complete configuration surface.

## Runtime behavior

- The application listens on port `8080` by default.
- Health checks target `/`, because the application does not expose Spring Boot actuator endpoints.
- Job state is stored in memory, so pod restarts clear the current job list.

## Troubleshooting

Render the chart locally:

```bash
helm template ipfix-generator ./chart
```

Inspect the deployed resources:

```bash
kubectl get all -n ipfix-generator
kubectl describe deployment -n ipfix-generator ipfix-generator
```

Follow application logs:

```bash
kubectl logs -n ipfix-generator -f deployment/ipfix-generator
```

Port-forward the service:

```bash
kubectl port-forward -n ipfix-generator svc/ipfix-generator 8080:8080
```

## License

[GNU GPL v3](../LICENSE)
