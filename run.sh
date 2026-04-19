#!/bin/bash

echo "========================================"
echo "   COMPILANDO PROYECTO SST"
echo "========================================"

# Ir al directorio del proyecto
cd /home/bjzr/Proyectos/java/sst/SST

# Compilar con Maven
echo "Compilando..."
mvn clean compile -DskipTests

if [ $? -eq 0 ]; then
    echo "✓ Compilación exitosa"
else
    echo "✗ Error en compilación"
    exit 1
fi

# Empaquetar
echo ""
echo "Empaquetando..."
mvn package -DskipTests

if [ $? -eq 0 ]; then
    echo "✓ Empaquetado exitoso"
else
    echo "✗ Error en empaquetado"
    exit 1
fi

echo ""
echo "========================================"
echo "   INICIANDO APLICACIÓN"
echo "========================================"
echo "Abre en tu navegador: http://localhost:8080"
echo "Usuario: admin@sgsst.com"
echo "Contraseña: admin123"
echo ""
echo "Para detener: Ctrl+C"
echo "========================================"

# Ejecutar
java -jar target/sgsst-1.0.0.jar