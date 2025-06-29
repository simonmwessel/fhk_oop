package de.fhkiel.oop.io

import de.fhkiel.oop.config.AppConfig
import de.fhkiel.oop.config.DefaultConfig
import de.fhkiel.oop.config.PlainConfig
import de.fhkiel.oop.model.BaseShape
import java.io.*
import java.nio.file.Files
import java.nio.file.Path

object DrawingIO {
    /**
     * Directory where sketches are stored.
     * This directory is created if it does not exist.
     */
    private val sketchDir = Path.of("sketches")

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
     * Saves a list of shapes to a file in the sketches directory.
     *
     * @param shapes the list of shapes to save
     * @param filename the name of the file to save to
     */
    fun save(shapes: List<BaseShape>, filename: String) {
        try {
            val path = sketchDir.resolve(filename)
            BufferedWriter(FileWriter(path.toFile())).use { writer ->
                shapes.forEach { shape ->
                    writer.write(ShapeSerializer.serialize(shape))
                    writer.newLine()
                }
            }
            println("Saved ${shapes.size} shape(s) to sketches/$filename")
        } catch (e: IOException) {
            println("Error saving to file 'sketches/$filename': ${e.message}")
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
        val shapes = mutableListOf<BaseShape>()
        try {
            val path = sketchDir.resolve(filename)
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
            println("Loaded ${shapes.size} shape(s) from sketches/$filename")
        } catch (e: IOException) {
            println("Error loading file 'sketches/$filename': ${e.message}")
        }
        return shapes
    }
}
