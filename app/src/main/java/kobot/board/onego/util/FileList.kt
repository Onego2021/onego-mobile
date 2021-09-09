package kobot.board.onego.util

import java.nio.file.attribute.FileTime

class FileList(var filename: String, var time: FileTime?, var size: String) {
    constructor(filename: String) : this(filename, null, "")
}