package org.isu_std.io.folder_setup;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Optional;

class FolderManagerChooserTest {
    @Test
    void pathMustBeCorrect(){
        Optional<Path> optionalPath = FolderChooser.getDirectory("Choose Document File Path");
        System.out.println(optionalPath.toString());
    }
}