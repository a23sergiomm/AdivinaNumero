import java.io.File

fun menu() {
    do {
        println("\u001B[35m----------------------------------\u001B[0m")
        println("\u001B[36m|              Menu              |\u001B[0m")
        println("\u001B[31m|           1. Jugar             |\u001B[0m")
        println("\u001B[34m| 2. Ver traza de último intento |\u001B[0m")
        println("\u001B[33m|           3. Salir             |\u001B[0m")
        println("\u001B[32m----------------------------------\u001B[0m")
        println("Selecciona una opción: ")

        val opcion = readln().toInt()

        when (opcion) {
            1 -> juego()
            2 -> verTraza()
            3 -> {
                break
                println("GRACIAS POR JUGAR")
            }
        }
    } while (opcion != 3)
}

fun generarNumero():String{
    var numOg = "123456"
    var numReal = numOg.toList().shuffled()
    var numSecr = ""
    for (i  in 0 until  4){
        numSecr += numReal[i].toString()
    }
    return numSecr
}

fun verTraza() {
    try {
        val filePath = "traza.txt"
        val lineas = File(filePath).readLines()

        if (lineas.isEmpty()) {
            println("No hay juegos registrados.")
        } else {
            lineas.forEachIndexed { index, line ->
                println("${index + 1}: $line") // Imprime cada línea con su número
            }
        }
    } catch (e: Exception) {
        println("Error al leer el archivo: ${e.message}")
    }
}

fun juego() {
    val numSecreto = generarNumero()
    var intentos = 10
    var resultadoPartida = ""

    println("Introduce un número de 4 dígitos que contengan números del 1 al 6: ")
    while (intentos > 0) {
        val numeroJugador = readln()
        var acierto = 0
        var coin = 0

        for (k in numeroJugador.indices) {
            when {
                numeroJugador[k] == numSecreto[k] -> acierto++
                numeroJugador[k] in numSecreto -> coin++
            }
        }

        println("$numeroJugador \u001B[42m $acierto  \u001B[43m $coin \u001B[0m  INTENTOS = $intentos")

        // Guardar detalles de la partida
        resultadoPartida = "$numeroJugador: \u001B[42m $acierto  \u001B[43m $coin \u001B[0m"

        if (acierto == 4) {
            resultadoPartida = "Ganaste! El número era $numSecreto. Detalles: $resultadoPartida"
            println("Has ganado la partida. \u001B[1m\u001B[33mFELICIDADES!!!! \u001B[0m")
            break
        }

        intentos--
        println("Introduce un número de 4 dígitos que contengan números del 1 al 6: ")
    }

    if (intentos == 0) {
        resultadoPartida = "Perdiste. El número era $numSecreto. Detalles: $resultadoPartida"
        println("OHHHHH!!! Te has quedado sin intentos. \u001B[31m HAS PERDIDO!!!\u001B[0m")
    }

    // Guardar resultado de la partida
    guardarResultado(resultadoPartida)
}

fun guardarResultado(resultado: String) {
    val filePath = "traza.txt"
    File(filePath).appendText("$resultado\n") // Agrega el resultado al final del archivo
}

fun main() {
    menu()
}
