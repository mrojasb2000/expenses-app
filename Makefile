GRADLE ?= ./gradlew

.PHONY: help build debug release test connected-test install-debug clean check validate

DEFAULT_GOAL := help

help:
	@echo "Makefile para el proyecto Android 'Expenses'"
	@echo "Uso: make <target>"
	@echo "Targets disponibles:"
	@echo "  help           - Muestra esta ayuda"
	@echo "  build          - Ejecuta 'gradle build' (compila todo)"
	@echo "  debug          - Ensambla APK debug (assembleDebug)"
	@echo "  release        - Ensambla APK release (assembleRelease)"
	@echo "  test           - Ejecuta pruebas unitarias (test)"
	@echo "  connected-test - Ejecuta pruebas instrumentadas en dispositivo/emulador"
	@echo "  install-debug  - Instala el APK debug en un dispositivo/emulador conectado"
	@echo "  check          - Ejecuta 'gradle check'"
	@echo "  clean          - Limpia artefactos de build"
	@echo "  validate       - Ejecuta una verificación rápida: clean, test y assembleDebug"

build:
	@echo "Ejecutando: $(GRADLE) build"
	$(GRADLE) build --no-daemon

debug:
	@echo "Ejecutando: $(GRADLE) assembleDebug"
	$(GRADLE) assembleDebug --no-daemon

release:
	@echo "Ejecutando: $(GRADLE) assembleRelease"
	$(GRADLE) assembleRelease --no-daemon

test:
	@echo "Ejecutando: $(GRADLE) test"
	$(GRADLE) test --no-daemon

connected-test:
	@echo "Ejecutando: $(GRADLE) connectedAndroidTest"
	$(GRADLE) connectedAndroidTest --no-daemon

install-debug:
	@echo "Ejecutando: $(GRADLE) installDebug"
	$(GRADLE) installDebug --no-daemon

check:
	@echo "Ejecutando: $(GRADLE) check"
	$(GRADLE) check --no-daemon

clean:
	@echo "Ejecutando: $(GRADLE) clean"
	$(GRADLE) clean

validate:
	@echo "Validación rápida: clean, test y assembleDebug"
	$(GRADLE) clean test assembleDebug --no-daemon

