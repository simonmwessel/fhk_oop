package de.fhkiel.oop.io

import de.fhkiel.oop.config.AppConfig
import de.fhkiel.oop.config.DefaultConfig
import de.fhkiel.oop.config.PlainConfig
import de.fhkiel.oop.model.BaseShape
import java.io.*
import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Collectors

object DrawingIO {
    /**
     * Directory where sketches are stored.
     * This directory is created if it does not exist.
     */
    private val sketchDir = Path.of("sketches")

    /**
     * Ensures the `.sketch` extension for a filename.
     */
    private fun ensureExtension(filename: String): String =
        if (filename.endsWith(".sketch")) filename else "$filename.sketch"

    /**
     * Initializes the sketches directory if it does not exist.
     */
    init {
        try {
            if (!Files.exists(sketchDir)) {
                Files.createDirectory(sketchDir)
                println("Created sketches directory")
            }
        } catch (e: IOException) {
            println("Error creating sketches directory: ${e.message}")
        }
    }

    /**
     * Lists all saved sketches in the sketches directory.
     */
    fun listSketches(): List<String> = try {
        Files.list(sketchDir)
            .filter { it.fileName.toString().endsWith(".sketch") }
            .map { it.fileName.toString() }
            .sorted()
            .collect(Collectors.toList())
    } catch (e: IOException) {
        println("Error listing sketches: ${e.message}")
        emptyList()
    }

    /**
     * Checks whether a sketch file already exists.
     */
    fun fileExists(filename: String): Boolean =
        Files.exists(sketchDir.resolve(ensureExtension(filename)))

    /**
     * Saves a list of shapes to a file in the sketches directory.
     *
     * @param shapes the list of shapes to save
     * @param filename the name of the file to save to (without extension)
     */
    fun save(shapes: List<BaseShape>, filename: String) {
        val actual = ensureExtension(filename)
        try {
            val path = sketchDir.resolve(actual)
            BufferedWriter(FileWriter(path.toFile())).use { writer ->
                shapes.forEach { shape ->
                    writer.write(ShapeSerializer.serialize(shape))
                    writer.newLine()
                }
            }
            println("Saved ${shapes.size} shape(s) to sketches/$actual")
        } catch (e: IOException) {
            println("Error saving to file 'sketches/$actual': ${e.message}")
        }
    }

    /**
     * Loads shapes from a file in the sketches directory.
     *
     * @param filename the name of the file to load from
     * @param config the configuration to use for deserialization
     * @return a list of shapes loaded from the file
     * @throws SketchParseException if the file cannot be parsed
     */
    @Throws(SketchParseException::class)
    fun load(filename: String, config: AppConfig = DefaultConfig): List<BaseShape> {
        val actual = ensureExtension(filename)
        val shapes = mutableListOf<BaseShape>()
        try {
            val path = sketchDir.resolve(actual)
            BufferedReader(FileReader(path.toFile())).useLines { lines ->
                lines.forEachIndexed { idx, line ->
                    if (line.isNotBlank()) {
                        try {
                            val parsed = ShapeSerializer.deserialize(line, PlainConfig, config)
                            shapes.add(parsed)
                        } catch (e: IllegalArgumentException) {
                            val msg = e.message ?: "Invalid line"
                            throw SketchParseException("Line ${idx + 1}: $msg")
                        }
                    }
                }
            }
            if (shapes.isEmpty()) {
                throw SketchParseException("No shapes in file")
            }
            println("Loaded ${shapes.size} shape(s) from sketches/$actual")
        } catch (e: IOException) {
            println("Error loading file 'sketches/$actual': ${e.message}")
        }
        return shapes
    }
}
