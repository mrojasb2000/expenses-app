# Restaurant (Android)

Descripción
-----------
Aplicación Android simple para gestionar pedidos en una mesa. Permite ingresar cantidades para dos ítems (Pastel de Choclo y Cazuela), calcula subtotal, propina (10%) y total. La UI facilita la edición de cantidades y permite activar/desactivar la propina.

Estado actual
------------
- Lenguaje: Kotlin
- Estructura: aplicación Android con Gradle y pruebas unitarias (JUnit) en `app/src/test`.
- Gradle wrapper incluido (usar `./gradlew`).

Principales componentes
----------------------
- `app/src/main/java/com/bramworks/tech/restaurant/MainActivity.kt`
  - Control de la UI y listeners.
  - Comportamientos importantes:
    - Al recibir foco en un EditText de cantidad: si el valor es "0" se limpia; si tiene otro valor se selecciona todo el texto.
    - Al perder foco: si el campo queda vacío se restaura a "0".
    - Al cambiar el Switch (`swEnableTips`) se actualiza la propiedad `acceptsTips` del `TableAccount` y se recalculan subtotal/propina/total.

- `app/src/main/java/com/bramworks/tech/restaurant/models/TableAccount.kt`
  - Lógica de negocio: administración de ítems, cálculo de subtotal, cálculo de propina (10%) y total.
  - Métodos principales: `AddItem(itemMenu, count)`, `AddItem(tableItem)`, `SetItemCount(itemMenu, count)`, `calculateTotalWithOutTips()`, `calculateTips()`, `calculateTotalWithTips()`.

- `app/src/main/java/com/bramworks/tech/restaurant/models/TableItem.kt`
  - Representa un ítem con su `ItemMenu` y `count`, y calcula su subtotal.

- `app/src/main/java/com/bramworks/tech/restaurant/models/ItemMenu.kt`
  - Modelo simple con `name` y `price` (price como String en este proyecto).

- Layout principal: `app/src/main/res/layout/activity_main.xml` contiene los EditText `etCountChoclo` y `etCountCasuela`, el `SwitchCompat` `swEnableTips` y TextViews para mostrar subtotal/propina/total.

Tests implementados
-------------------
Se agregaron tests unitarios para cubrir las rutas críticas de modelos:

- `app/src/test/java/com/bramworks/tech/restaurant/models/TableAccountTest.kt`
  - Happy path: verifica subtotal, propina (10%) y total cuando se añaden items válidos y `acceptsTips = true`.
  - Sad path: verifica que counts inválidos (0 y negativos) se ignoren y que con propinas deshabilitadas los totales sean 0.

- `app/src/test/java/com/bramworks/tech/restaurant/models/TableItemTest.kt`
  - Verifica `calculateSubTotal()` para counts positivos y no positivos.

- `app/src/test/java/com/bramworks/tech/restaurant/models/TableAccountMethodsTest.kt`
  - Verifica `SetItemCount()` (añade y remueve items) y `AddItem(tableItem)` (reemplaza counts y ignora counts no positivos).

- `app/src/test/java/com/bramworks/tech/restaurant/models/TableAccountAdditionalTest.kt`
  - Casos adicionales: reemplazo por nombre con whitespace, sumas de múltiples ítems, y comportamiento de truncamiento de la propina (toInt).

Todos los tests pasan en mi ejecución local usando `./gradlew test`.

Makefile
--------
Se añadió un `Makefile` en la raíz para facilitar operaciones comunes. Targets relevantes:

- `make help` — muestra ayuda.
- `make debug` — `./gradlew assembleDebug`.
- `make test` — `./gradlew test`.
- `make validate` — `./gradlew clean test assembleDebug`.

Ejemplos de uso
---------------
Construir debug:
```bash
make debug
```

Ejecutar tests unitarios:
```bash
make test
```

Validación rápida (limpia, prueba y compila debug):
```bash
make validate
```

Cómo ejecutar la app
-------------------
- Abrir el proyecto en Android Studio y ejecutar en un emulador o dispositivo.
- O generar el APK (`make debug`) y luego instalarlo con:
```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

Notas y mejoras aplicadas
------------------------
- El `Switch` del layout fue migrado a `SwitchCompat` para mejor compatibilidad y coherencia con AppCompat.
- Se reemplazaron strings hardcodeados "0" por el recurso `@string/restaurant_zero`.
- Se añadieron múltiples tests unitarios para cubrir casos felices y de error.

Mejoras sugeridas (opcionales)
-----------------------------
- Limpiar advertencias restantes en `TableAccount.kt` (p. ej. el parámetro `table` actualmente no se usa). Puedo:
  - eliminar el parámetro si no es necesario, o
  - usarlo (p. ej. exponer `table` en toString) o
  - suprimir la advertencia con anotaciones.
- Añadir pruebas de UI con Robolectric o instrumentadas para verificar comportamiento de focus y listeners.
- Reemplazar colores/tamaños hardcodeados por recursos para facilitar theming.

Contacto / siguientes pasos
---------------------------
Si quieres que aplique alguna de las mejoras sugeridas (limpieza de warnings, migración completa a Material Components, tests de UI), dime cuál y la implemento.

---
Archivo actualizado para reflejar las modificaciones recientes: manejo de foco en EditText, switch de propinas con SwitchCompat, tests unitarios y Makefile.

