package org.isu_std.io.folder_setup;

import org.isu_std.io.Util;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FolderChooserTest {
    @Test
    void pathMustBeCorrect(){
        Optional<Path> optionalPath = FolderChooser.getPath("Choose Document File Path");
        System.out.println(optionalPath.toString());
    }
}