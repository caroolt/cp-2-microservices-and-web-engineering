#!/usr/bin/env bash

set -e

apt update
apt install -y jq

# Determinar o host da API (api se estiver na mesma rede Docker, localhost se não)
API_HOST=${API_HOST:-api}
API_PORT=${API_PORT:-8080}
API_URL="http://${API_HOST}:${API_PORT}"

# Criar Autor (necessário para criar livro)
HTTP_STATUS=$(
    curl -X 'POST' \
    "${API_URL}/api/v2/authors" \
    -H 'accept: */*' \
    -H 'Content-Type: application/json' \
    -w "%{http_code}" \
    -o author_create.json \
    -d '{
    "name": "Machado de Assis",
    "birthDate": "1839-06-21"
    }'
)
echo "Status HTTP: $HTTP_STATUS"
if [ "$HTTP_STATUS" -ne 201 ]; then
    echo "Erro ao criar autor"
    exit 1  
fi

AUTHOR_ID=$(jq '.id' author_create.json)

# POST - Criar Livro
HTTP_STATUS=$(
    curl -X 'POST' \
    "${API_URL}/api/v2/books" \
    -H 'accept: */*' \
    -H 'Content-Type: application/json' \
    -w "%{http_code}" \
    -o book_create.json \
    -d "{
    \"title\": \"Dom Casmurro\",
    \"description\": \"Romance de Machado de Assis\",
    \"authorId\": $AUTHOR_ID
    }"
)
echo "Status HTTP: $HTTP_STATUS"
if [ "$HTTP_STATUS" -ne 201 ]; then
    echo "Erro ao criar livro"
    exit 1  
fi

BOOK_ID=$(jq '.id' book_create.json)

echo "Livro criado com ID: $BOOK_ID"

# GET - Buscar Livro por ID
HTTP_STATUS=$(curl -X GET "${API_URL}/api/v2/books/$BOOK_ID" -o book_get.json -w "%{http_code}" -H 'accept: */*')
echo "Status HTTP: $HTTP_STATUS"
if [ "$HTTP_STATUS" -ne 200 ]; then
    echo "Erro ao buscar livro"
    exit 1  
fi

# PUT - Atualizar Livro
HTTP_STATUS=$(
    curl -X 'PUT' \
    "${API_URL}/api/v2/books/$BOOK_ID" \
    -H 'accept: */*' \
    -H 'Content-Type: application/json' \
    -w "%{http_code}" \
    -o book_update.json \
    -d "{
    \"title\": \"Dom Casmurro (Atualizado)\",
    \"description\": \"Romance clássico de Machado de Assis\",
    \"authorId\": $AUTHOR_ID
    }"
)
echo "Status HTTP: $HTTP_STATUS"
if [ "$HTTP_STATUS" -ne 200 ]; then
    echo "Erro ao atualizar livro"
    exit 1
fi

# GET - Listar todos os livros
HTTP_STATUS=$(curl -X GET "${API_URL}/api/v2/books" -o book_list.json -w "%{http_code}" -H 'accept: */*')
echo "Status HTTP: $HTTP_STATUS"
if [ "$HTTP_STATUS" -ne 200 ]; then
    echo "Erro ao listar livros"
    exit 1  
fi

# DELETE - Deletar Livro
HTTP_STATUS=$(
    curl -X 'DELETE' \
    "${API_URL}/api/v2/books/$BOOK_ID" \
    -H 'accept: */*' \
    -w "%{http_code}"
)
echo "Status HTTP: $HTTP_STATUS"
if [ "$HTTP_STATUS" -ne 204 ]; then
    echo "Erro ao deletar livro"
    exit 1
fi

