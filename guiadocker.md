## Guía: Construir y desplegar el contenedor en Google Cloud Run

### 1. Requisitos previos

- Tener instalado Docker.
- Tener instalado el SDK de Google Cloud.
- Tener un proyecto creado en Google Cloud y conocer el `PROJECT_ID`.

### 2. Autenticarse y configurar gcloud (una sola vez)

```bash
gcloud auth login
gcloud auth application-default login

gcloud config set project TU_PROJECT_ID
gcloud config set run/region europe-southwest1
```

> Reemplazar `TU_PROJECT_ID` por el ID real del proyecto de Google Cloud.

### 3. Crear el repositorio de contenedores (Artifact Registry)

Este paso se hace solo la primera vez para crear el repositorio Docker en Artifact Registry.

```bash
gcloud artifacts repositories create web-examen-repo \
  --repository-format=docker \
  --location=europe-southwest1 \
  --description="Repo del examen"
```

Si estás en Windows y la consola no acepta `\` al final de línea, puedes escribirlo todo en una sola línea:

```bash
gcloud artifacts repositories create web-examen-repo --repository-format=docker --location=europe-southwest1 --description="Repo del examen"
```

### 4. Configurar Docker para usar Artifact Registry (una vez por PC)

```bash
gcloud auth configure-docker europe-southwest1-docker.pkg.dev
```

### 5. Construir la imagen Docker localmente

Desde la carpeta raíz del proyecto, donde está el `Dockerfile`:

```bash
docker build -t web-examen .
```

### 6. Etiquetar y subir la imagen a Artifact Registry

```bash
docker tag web-examen europe-southwest1-docker.pkg.dev/TU_PROJECT_ID/web-examen-repo/web-examen:latest

docker push europe-southwest1-docker.pkg.dev/TU_PROJECT_ID/web-examen-repo/web-examen:latest
```

> De nuevo, reemplazar `TU_PROJECT_ID` por el ID real del proyecto.

### 7. Desplegar la imagen en Cloud Run

En Windows, escribir el comando en **una sola línea**:

```bash
 