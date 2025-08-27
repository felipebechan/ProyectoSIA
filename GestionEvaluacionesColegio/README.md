# Avance 1: Sistema de Gestion de Evaluaciones

Este es el avance del primer proyecto (SIA).

### Integrantes
- VICENTE ...
- FELIPE BECHAN 21.175.767-1
- GIOVANI FAÚNDEZ 21.084.192-k

### RESUMEN
Es un programa de consola para gestionar evaluaciones de un colegio.
La idea es que puedes crear asignaturas (como 'matematicas') y dentro de cada una, crear 'bancos de preguntas' por temas (como 'algebra').

Puedes agregar y listar bancos, y tambien agregar y ver las preguntas de cada banco.

### Como ejecutarlo
1. clonar el repo.
2. abrir el proyecto en netbeans o eclipse o VS.
3. corre el archivo `Main.java`.
4. listo.

necesitas tener java jdk 11 instalado pa que funcione.

---

### Cumplimiento de la Rubrica (Avance SIA)

- **SIA1.1 (Analisis):** El analisis es este mismo sistema: gestion de asignaturas y sus bancos de preguntas. Las funcionalidades son agregar y listar ambos.
- **SIA1.2 (Clases):** Se crearon las clases `Asignatura`, `BancoDePreguntas` y `Pregunta`. Estan en la carpeta `src/`.
- **SIA1.3 (Getters/Setters):** Todas las clases tienen sus atributos en `private` y sus metodos get y set.
- **SIA1.4 (Datos Iniciales):** En `Main.java`, el metodo `cargarDatosIniciales()` crea un par de asignaturas y bancos pa que el programa no parta vacio.
- **SIA1.5 (Colecciones Anidadas):** La primera coleccion es el `HashMap` de `Asignatura` en `Main.java`. La coleccion anidada es el `ArrayList` de `BancoDePreguntas` dentro de la clase `Asignatura`.
- **SIA1.6 (Sobrecarga):** La clase `BancoDePreguntas` tiene un metodo sobrecargado `agregarPregunta()`. Uno recibe el objeto `Pregunta` y el otro recibe los datos para crearlo ahi mismo.
- **SIA1.7 (Mapa):** Se usa un `HashMap<String, Asignatura>` en `Main.java` para guardar y buscar las asignaturas por su codigo. Es mas rapido.
- **SIA1.8 (Menu 2da coleccion):** El menu, despues de elegir una asignatura, te deja agregar y listar los bancos de preguntas (la coleccion anidada). Se hace en el metodo `gestionarAsignatura()` en `Main.java`.
- **SIA1.9 (Consola):** Todo el programa funciona por consola, usando `System.out.println()` y `Scanner`.
- **SIA1.10 (GitHub):** Se han hecho los 3 commits (o mas) requeridos para el avance.

---

### Cosas por hacer / Ideas (TODO)

- [✓] Implementar la parte básica del proyecto (obvio).
- [ ] Crear 'Evaluaciones' de "*verdad*", usando las preguntas de los bancos.
- [ ] Agregar mas tipos de preguntas (alternativas, verdadero/falso).
- [ ] Mejorar el manejo de errores, que el programa no se caiga si metes una letra en vez de un numero.
- [ ] Pasar todo a interfaz grafica con Swing pa la entrega final.
- [ ] Agregar persistencia para guardar los datos en un archivo y que no se borren (podriamos usar sqlite, soy fan jaja, con maven tmb).