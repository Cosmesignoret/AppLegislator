
package com.duoc.app.ui.material

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

private val AzulPrincipal = Color(0xFF205C95)
private val AzulOscuro = Color(0xFF174666)
private val FondoCeleste = Color(0xFFEDF5FA)

private data class MaterialJuridico(
    val titulo: String,
    val categoria: String,
    val descripcion: String,
    val contenido: String
)

private val jurisprudencia = listOf(
    MaterialJuridico(
        titulo = "Cómo leer una sentencia judicial",
        categoria = "Guía de jurisprudencia",
        descripcion = "Conoce las partes principales de una sentencia y cómo analizarla.",
        contenido = """
            Una sentencia judicial es una resolución mediante la cual un tribunal decide una controversia o una cuestión sometida a su conocimiento.

            Para estudiarla, identifica primero el tribunal que la dicta, la fecha, las partes y la materia discutida.

            Luego revisa los antecedentes del caso, los argumentos jurídicos y los fundamentos utilizados por el tribunal.

            Finalmente, analiza la decisión y explica qué normas o principios fueron relevantes.

            Esta guía es material educativo y no corresponde a una sentencia real.
        """.trimIndent()
    ),
    MaterialJuridico(
        titulo = "Partes de una resolución judicial",
        categoria = "Análisis jurídico",
        descripcion = "Aprende a reconocer los antecedentes, fundamentos y decisiones.",
        contenido = """
            Las resoluciones judiciales pueden presentar distintas estructuras según su naturaleza.

            En una sentencia definitiva chilena es habitual encontrar una parte expositiva, una parte considerativa y una parte resolutiva.

            La parte expositiva presenta los antecedentes relevantes del proceso.

            La parte considerativa desarrolla el razonamiento del tribunal y sus fundamentos.

            La parte resolutiva expresa la decisión adoptada.

            Al estudiar una sentencia, relaciona cada fundamento con la conclusión del tribunal.
        """.trimIndent()
    ),
    MaterialJuridico(
        titulo = "Interpretación de normas jurídicas",
        categoria = "Conceptos de jurisprudencia",
        descripcion = "Comprende cómo los tribunales interpretan y aplican las normas.",
        contenido = """
            Interpretar una norma jurídica consiste en determinar su sentido y alcance para aplicarla a una situación concreta.

            En el estudio del Derecho chileno se consideran elementos de interpretación como el gramatical, lógico, histórico y sistemático.

            Los tribunales pueden examinar el texto de la norma, su contexto y su relación con otras disposiciones.

            Al analizar jurisprudencia, identifica qué interpretación adoptó el tribunal y cómo justificó su decisión.

            Este contenido es una introducción general y no reemplaza el estudio de las normas aplicables.
        """.trimIndent()
    )
)

private val doctrina = listOf(
    MaterialJuridico(
        titulo = "Derecho Civil",
        categoria = "Doctrina jurídica",
        descripcion = "Personas, bienes, obligaciones y contratos.",
        contenido = """
            El Derecho Civil regula diversas relaciones jurídicas entre personas y aspectos de su patrimonio.

            Entre sus materias de estudio se encuentran las personas, los bienes, las obligaciones, los contratos y la responsabilidad civil.

            Las obligaciones pueden surgir de distintas fuentes reconocidas por el ordenamiento jurídico.

            Los contratos permiten establecer acuerdos que producen efectos jurídicos cuando cumplen los requisitos correspondientes.

            Para profundizar, puedes estudiar el Código Civil chileno y los materiales de tu asignatura.
        """.trimIndent()
    ),
    MaterialJuridico(
        titulo = "Derecho Penal",
        categoria = "Doctrina jurídica",
        descripcion = "Principios penales y conceptos básicos del delito.",
        contenido = """
            El Derecho Penal estudia las conductas que la ley establece como delitos y las consecuencias jurídicas asociadas.

            Uno de sus principios fundamentales es el de legalidad, según el cual los delitos y las penas deben estar establecidos por la ley.

            La teoría del delito permite analizar los elementos necesarios para determinar si una conducta constituye una infracción penal.

            Entre los conceptos de estudio se encuentran la tipicidad, la antijuridicidad y la culpabilidad.

            Este resumen tiene una finalidad introductoria.
        """.trimIndent()
    ),
    MaterialJuridico(
        titulo = "Derecho Constitucional",
        categoria = "Doctrina jurídica",
        descripcion = "Constitución, derechos fundamentales y organización del Estado.",
        contenido = """
            El Derecho Constitucional estudia la organización del Estado, el ejercicio del poder público y la protección de los derechos fundamentales.

            La Constitución establece normas esenciales sobre las instituciones del Estado y sus competencias.

            También reconoce derechos y establece mecanismos relacionados con su protección.

            Para estudiar esta materia, es importante comprender la supremacía constitucional y la distribución de funciones entre los órganos del Estado.

            Revisa siempre el texto constitucional vigente para estudiar sus disposiciones específicas.
        """.trimIndent()
    ),
    MaterialJuridico(
        titulo = "Fuentes del Derecho",
        categoria = "Teoría del Derecho",
        descripcion = "Introducción a la ley, la costumbre, la jurisprudencia y la doctrina.",
        contenido = """
            Las fuentes del Derecho permiten estudiar el origen y las formas de manifestación de las normas jurídicas.

            La ley es una fuente formal fundamental del ordenamiento jurídico chileno.

            La costumbre puede tener relevancia jurídica en los casos y condiciones reconocidos por el ordenamiento.

            La jurisprudencia reúne decisiones de los tribunales y resulta útil para estudiar cómo se aplican e interpretan las normas.

            La doctrina corresponde al estudio y análisis del Derecho realizado por especialistas.

            La función y el valor de cada fuente dependen del sistema jurídico y de la materia estudiada.
        """.trimIndent()
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaterialScreen(volver: () -> Unit) {
    var pestana by remember { mutableIntStateOf(0) }
    var busqueda by remember { mutableStateOf("") }
    var materialSeleccionado by remember {
        mutableStateOf<MaterialJuridico?>(null)
    }

    val materiales = if (pestana == 0) jurisprudencia else doctrina

    val materialesFiltrados = materiales.filter {
        it.titulo.contains(busqueda, ignoreCase = true) ||
                it.categoria.contains(busqueda, ignoreCase = true) ||
                it.descripcion.contains(busqueda, ignoreCase = true)
    }

    materialSeleccionado?.let { material ->
        AlertDialog(
            onDismissRequest = { materialSeleccionado = null },
            title = {
                Text(
                    text = material.titulo,
                    color = AzulOscuro,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                androidx.compose.foundation.layout.Column(
                    modifier = Modifier
                        .heightIn(max = 400.dp)
                        .then(
                            Modifier
                        )
                ) {
                    androidx.compose.foundation.layout.Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(
                                androidx.compose.foundation.rememberScrollState()
                            )
                    ) {
                        Text(
                            text = material.contenido,
                            color = Color.DarkGray,
                            lineHeight = 22.sp
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = { materialSeleccionado = null }
                ) {
                    Text("Cerrar", color = AzulPrincipal)
                }
            },
            shape = RoundedCornerShape(20.dp),
            containerColor = Color.White
        )
    }

    Scaffold(
        containerColor = FondoCeleste,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = volver) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                },
                title = {
                    Text(
                        text = "Material Complementario",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AzulPrincipal
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            TabRow(
                selectedTabIndex = pestana,
                containerColor = Color.White,
                contentColor = AzulPrincipal
            ) {
                Tab(
                    selected = pestana == 0,
                    onClick = {
                        pestana = 0
                        busqueda = ""
                    },
                    text = {
                        Text(
                            "Jurisprudencia",
                            fontWeight = if (pestana == 0) {
                                FontWeight.Bold
                            } else {
                                FontWeight.Normal
                            }
                        )
                    }
                )

                Tab(
                    selected = pestana == 1,
                    onClick = {
                        pestana = 1
                        busqueda = ""
                    },
                    text = {
                        Text(
                            "Doctrina",
                            fontWeight = if (pestana == 1) {
                                FontWeight.Bold
                            } else {
                                FontWeight.Normal
                            }
                        )
                    }
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item {
                    Text(
                        text = if (pestana == 0) {
                            "Jurisprudencia"
                        } else {
                            "Doctrina jurídica"
                        },
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = AzulOscuro
                    )

                    Spacer(Modifier.height(6.dp))

                    Text(
                        text = if (pestana == 0) {
                            "Aprende a interpretar y analizar decisiones judiciales."
                        } else {
                            "Explora conceptos fundamentales para tus estudios jurídicos."
                        },
                        fontSize = 14.sp,
                        color = Color.DarkGray
                    )
                }

                item {
                    OutlinedTextField(
                        value = busqueda,
                        onValueChange = { busqueda = it },
                        label = { Text("Buscar material") },
                        placeholder = { Text("Escribe un tema...") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AzulPrincipal,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        )
                    )
                }

                if (materialesFiltrados.isEmpty()) {
                    item {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "No se encontraron materiales con esa búsqueda.",
                                modifier = Modifier.padding(20.dp),
                                color = Color.DarkGray
                            )
                        }
                    }
                } else {
                    items(materialesFiltrados) { material ->
                        TarjetaMaterial(
                            titulo = material.titulo,
                            descripcion = material.descripcion,
                            categoria = material.categoria,
                            icono = if (pestana == 0) {
                                Icons.Default.Description
                            } else {
                                Icons.Default.Book
                            },
                            onClick = {
                                materialSeleccionado = material
                            }
                        )
                    }
                }

                item {
                    Text(
                        text = "Contenido introductorio de estudio. No corresponde a sentencias oficiales ni sustituye fuentes jurídicas actualizadas.",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TarjetaMaterial(
    titulo: String,
    descripcion: String,
    categoria: String = "Material de estudio",
    icono: ImageVector = Icons.Default.Book,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = FondoCeleste
            ) {
                Icon(
                    imageVector = icono,
                    contentDescription = null,
                    tint = AzulPrincipal,
                    modifier = Modifier.padding(12.dp)
                )
            }

            Spacer(Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = categoria,
                    fontSize = 12.sp,
                    color = AzulPrincipal,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = titulo,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = AzulOscuro
                )

                Text(
                    text = descripcion,
                    fontSize = 13.sp,
                    color = Color.DarkGray,
                    lineHeight = 18.sp
                )

                Text(
                    text = "Toca para leer",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = AzulPrincipal
                )
            }
        }
    }
}
