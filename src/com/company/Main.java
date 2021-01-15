package com.company;

import com.GUI.Window;
import com.LOKI97LowLevel.*;

public class Main {

    public static void main(String[] args) {

        SBoxesGeneration.init();
        PermutationGeneration.init();

        new Window();
    }
}
