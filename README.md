# Sistema de Gestion de Evaluaciones (Entrega Final)

Este es el proyecto final del Sistema de Información (SIA). Ahora es una aplicación de escritorio completa con interfaz gráfica.

### Integrantes


*   FELIPE BECHAN 21.175.767-1
*   VICENTE RODRIGUEZ 21.768.082-4
*   GIOVANI FAÚNDEZ 21.084.192-k

---

### RESUMEN

El proyecto pasó de ser un programa de consola a una **aplicación de escritorio con interfaz gráfica (hecha en Swing)**. La idea principal sigue siendo la misma: gestionar asignaturas, bancos de preguntas y preguntas, pero ahora con muchas más funcionalidades.

La aplicación tiene **dos roles de usuario**: Profesor y Alumno.

*   **El Profesor puede:**
    *   Gestionar Asignaturas (crear, modificar, eliminar).
    *   Gestionar Bancos de Preguntas y Preguntas dentro de cada asignatura.
    *   Administrar usuarios (agregar nuevos alumnos y profesores).
    *   Ver reportes de promedios por asignatura.
    *   Exportar todas las notas a un archivo CSV.
*   **El Alumno puede:**
    *   Ver las asignaturas disponibles.
    *   **Tomar una prueba** de una asignatura, con preguntas al azar.
    *   Recibir su nota al instante después de terminar.

Todos los datos (usuarios, asignaturas, preguntas y notas) **se guardan en archivos CSV**, así que la información no se pierde al cerrar el programa.

### Arquitectura

El proyecto está organizado bajo el patrón **Modelo-Vista-Controlador (MVC)** para separar la lógica de la interfaz.

*   **Modelo:** Contiene toda la lógica de negocio y las clases de datos (`SistemaEvaluaciones`, `GestorArchivos`, `Asignatura`, etc.).
*   **Vista:** Contiene todas las ventanas de la interfaz gráfica (`VentanaPrincipal`, `VentanaLogin`, etc.).
*   **Controlador:** La clase `AppController` que actúa como intermediario.

### Como ejecutarlo

1.  clonar el repo.
2.  abrir el proyecto (que ahora es un proyecto Maven) en NetBeans.
3.  correr el archivo `Main.java`.
4.  listo. Aparecerá la ventana de login.

**Usuarios de prueba para entrar:**
*   **Alumno:** rut `1-1`
*   **Profesor:** rut `2-2`

Necesitas tener **Java JDK 11** instalado para que funcione.

---

### Cumplimiento de la Rúbrica (SIA Final)

*   **SIA2.1 (Diagrama UML):** El diagrama UML actualizado está en el repo y refleja la nueva arquitectura MVC.
*   **SIA2.2 (Persistencia):** Los datos se guardan en 4 archivos CSV (`usuarios.csv`, `asignaturas.csv`, `preguntas.csv`, `notas.csv`). El sistema los carga al iniciar y los guarda al salir.
*   **SIA2.3 (Interfaz Gráfica SWING):** Toda xsla interacción con el usuario ahora es a través de ventanas de Swing.
*   **SIA2.4 (Edición/Eliminación 2ª colección):** En la vista de profesor, al gestionar los bancos de una asignatura, se pueden editar y eliminar (CRUD completo).
*   **SIA2.5 (Funcionalidad Propia - Filtro):** Se implementaron varias funcionalidades propias:
    *   **Reporte de promedios:** El profesor puede ver el promedio de notas de cada asignatura.
    *   **Exportación de datos:** El profesor puede exportar todas las notas a un archivo CSV.
    *   **Sistema de roles:** La interfaz cambia dependiendo si entra un alumno o un profesor.
*   **SIA2.6 (Modularización):** El código está bien separado gracias a la arquitectura MVC y la clase `GestorArchivos` que se encarga solo de la persistencia.
*   **SIA2.7 (Sobreescritura):** Las clases `Alumno` y `Profesor` sobreescriben el método `mostrarInfo()` de la clase abstracta `Persona`.
*   **SIA2.8 (Try-Catch):** Toda la aplicación maneja errores con bloques `try-catch`, mostrando mensajes amigables al usuario con `JOptionPane` en vez de crashear.
*   **SIA2.9 (Excepciones personalizadas):** Se usan `ElementoDuplicadoException` y `ElementoNoEncontradoException` en la lógica del modelo.
*   **SIA2.10 (Reporte en TXT):** El profesor puede generar un reporte en `reporte.txt` que lista las asignaturas y sus bancos de preguntas.
*   **SIA2.11 (GitHub):** Se han hecho los commits adicionales requeridos.
*   **SIA2.12 (CRUD 1ra colección):** La ventana principal permite al profesor hacer CRUD completo sobre las `Asignaturas`.
*   **SIA2.13 (Búsqueda):** La búsqueda se implementa de forma visual al seleccionar elementos en las listas para gestionarlos en los niveles inferiores (Asignatura -> Bancos -> Preguntas).

