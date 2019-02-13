package com.lkprogramator.tool_images.model;

import com.lkprogramator.tool_images.testBase.baseTestClass;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class OptionsTest extends baseTestClass {

    @Test
    @DisplayName("Test if option has filter")
    void isFilterPresent() {

        Options options = new Options();
        options.setRotate(60);
        assertTrue(options.isFilterPresent(),"Options filter are present." );
    }

    @Test
    @DisplayName("Determined resize variant")
    void getResizeVariant() {

        Options options = new Options();
        options.setResizeByPercent(0.5f);

        assertEquals(ResizeVariants.RESIZE_PRC, options.getResizeVariant(),"Options obtain resize variant " );
    }

    @Test
    @DisplayName("Output Folder From Source")
    void getOutputFolderFromSource() {

        Options options = new Options();
        options.setSourcePath(pathToBaseImageFolder + baseImageName);

        assertEquals( pathToBaseImageFolder, options.getOutputFolderPath(), "Options get Output Folder From Source ");
    }
}
