

Examen Programación y Lenguajes de Marcas
Tercera evaluación


## Enunciado

Diseña y genera una plataforma Web mediante Jakarta EE, usando el patrón de diseño
MVC, que cuente con peticiones asíncronas mediante promesas, por medio de las cuales
se establezca una intercomunicación entre Cliente y Servidor, de tal modo que se pueda
acceder a información contenida en una base de datos gestionada por MySQL.

La aplicación debe implementarse y posteriormente desplegarse a través de Docker.

La aplicación debe desplegarse por medio de diferentes contenedores orquestados a través
de Docker Compose en https://railway.com/ (Plan Free).


Planteamiento formal

Desarrolla todos los procesos correspondientes a un CRUD:

● C: create, es decir “insert into t1...”
● R: read, es decir “select * from t1...”
● U: update, es decir “update t1 set...”
● D: delete, es decir “delete from t1...”


Criterios de calificación para lenguajes de marcas
1.-Bocetación visual y diseño gráfico (hasta 3 puntos)
## Objetivo
Definir el aspecto visual del proyecto antes de programar.
Tareas principales
● Diseño de bocetos (wireframes) de alta fidelidad para ordenador, tableta y móvil
● Diseño de interfaces (UI)
● Definición de componentes visuales
Herramienta para el desarrollo
● Design systems: Figma


2.-Guía de estilo y sistema de diseño (hasta 2 puntos)
## Objetivo
Garantizar coherencia visual y facilitar el desarrollo y mantenimiento.
Tareas principales
● Definición de paleta de colores
● Definición de las tipografías y jerarquías
● Definición de los formularios, controles e iconografía
● Definición de los estados (hover, error, éxito, etc...)
Herramienta para el desarrollo

● Design systems: Figma
3.-Desarrollo del código frontend (hasta 5 puntos)

A.-HTML y CSS (hasta 2 puntos de los 5 correspondientes al desarrollo del código frontend)

● Vistas (JSP o HTML) para la interfaz de usuario
● CSS con Flexbox o Grid
● Medidas relativas, nunca absolutas (em, %, rem, vh/vw)
● Diseño adaptativo (diseño funcional en distintos tamaños de pantalla usando media
queries)
● Textos legibles (buen contraste, tamaños adecuados)
● Navegación accesible (uso de alt en imágenes, label en formularios, navegación con
teclado)

B.-JavaScript (hasta 3 puntos de los 5 correspondientes al desarrollo del código frontend)

● Peticiones asíncronas mediante promesas (fetch) con envío y recepción de datos
mediante JSON (uso de GET o POST)
● Manejo de errores en then() y catch()

Criterios de calificación para programación
4.-Desarrollo del código backend

A.-Organización del Proyecto (Arquitectura MVC) (hasta 3 puntos)

● Estructura coherente (/model, /controller, /view)

B.-Desarrollo del Proyecto (Arquitectura MVC) (hasta 5 puntos)


● Modelos: clases Java que representan entidades y lógica de negocio, desarrollando
modelos aislados para cada proceso (uno para buscar, otro para modificar...).
● Controladores (Servlets): clases Java que gestionan las peticiones y respuestas,
desarrollando controladores aislados para cada proceso (uno para buscar, otro para
modificar...).

C.-Despliegue del Proyecto (hasta 2 puntos)

● Despliega el proyecto por medio de diferentes contenedores orquestados a través de
Docker Compose en https://railway.com/ (Plan Free).


Entrega el proyecto

Exporta desde el IDE el proyecto a zip y envíalo a través del Campus (incluye en el
proyecto un fichero denominado “informacion.md” (Markdown) en el que esté incluida la url
de figma en la que has desarrollado la bocetación visual, el diseño gráfico, la guía de estilo
y el sistema de diseño.


¿Qué es lo que no está permitido?

● Que dos o más compañeros de clase elijan el mismo tipo de proyecto. El profesor
debe aceptar previamente el proyecto que se va a realizar por el alumno de forma
individual.
● Que el código esté comentado ya que el profesor en la presentación del proyecto va
a preguntar sobre el significado, contexto o lógica de este al alumno para que
ofrezca una explicación sobre lo que ha hecho.


¿Qué pasa si...

● uso otras tecnologías (lenguajes, librerías, marcos de trabajo) diferentes de las
indicadas?
## QUE SUSPENDO

● el profesor selecciona una o varias líneas del proyecto y me pide que las explique
pero el profesor considera que no esclarece el sentido del código?
## QUE SUSPENDO