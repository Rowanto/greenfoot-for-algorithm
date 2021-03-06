/*
 * Copyright (c) 1998, 2005, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 * 
 * --- end of original header ---
 * 
 * This file was modified for use in the BlueJ program on the 1st September 2011.
 * 
 */

package bluej.doclet.doclets.formats.html;

import bluej.doclet.doclets.internal.toolkit.util.*;

import java.io.*;

/**
 * Writes the style sheet for the doclet output.
 *
 * @author Atul M Dambalkar
 */
public class StylesheetWriter extends HtmlDocletWriter {

    /**
     * Constructor.
     */
    public StylesheetWriter(ConfigurationImpl configuration,
                            String filename) throws IOException {
        super(configuration, filename);
    }

    /**
     * Generate the style file contents.
     * @throws DocletAbortException
     */
    public static void generate(ConfigurationImpl configuration) {
        StylesheetWriter stylegen;
        String filename = "";
        try {
            filename = "stylesheet.css";
            stylegen = new StylesheetWriter(configuration, filename);
            stylegen.generateStyleFile();
            stylegen.close();
        } catch (IOException exc) {
            configuration.standardmessage.error(
                        "doclet.exception_encountered",
                        exc.toString(), filename);
            throw new DocletAbortException();
        }
    }

    /**
     * Generate the style file contents.
     */
    protected void generateStyleFile() {
        print("/* "); printText("doclet.Style_line_1"); println(" */");
        println("");

        print("/* "); printText("doclet.Style_line_2"); println(" */");
        println("");

        print("/* "); printText("doclet.Style_line_3"); println(" */");
        println("body { background-color: #FFFFFF; color:#000000 }");
        println("");

        print("/* "); printText("doclet.Style_Headings"); println(" */");
        println("h1 { font-size: 145% }");
        println("");

        print("/* "); printText("doclet.Style_line_4"); println(" */");
        print(".TableHeadingColor     { background: #CCCCFF; color:#000000 }");
        print(" /* "); printText("doclet.Style_line_5"); println(" */");
        print(".TableSubHeadingColor  { background: #EEEEFF; color:#000000 }");
        print(" /* "); printText("doclet.Style_line_6"); println(" */");
        print(".TableRowColor         { background: #FFFFFF; color:#000000 }");
        print(" /* "); printText("doclet.Style_line_7"); println(" */");
        println("");

        print("/* "); printText("doclet.Style_line_8"); println(" */");
        println(".FrameTitleFont   { font-size: 100%; font-family: Helvetica, Arial, sans-serif; color:#000000 }");
        println(".FrameHeadingFont { font-size:  90%; font-family: Helvetica, Arial, sans-serif; color:#000000 }");
        println(".FrameItemFont    { font-size:  90%; font-family: Helvetica, Arial, sans-serif; color:#000000 }");
        println("");

       // Removed doclet.Style_line_9 as no longer needed

        print("/* "); printText("doclet.Style_line_10"); println(" */");
        print(".NavBarCell1    { background-color:#EEEEFF; color:#000000}");
        print(" /* "); printText("doclet.Style_line_6"); println(" */");
        print(".NavBarCell1Rev { background-color:#00008B; color:#FFFFFF}");
        print(" /* "); printText("doclet.Style_line_11"); println(" */");

        print(".NavBarFont1    { font-family: Arial, Helvetica, sans-serif; color:#000000;");
        println("color:#000000;}");
        print(".NavBarFont1Rev { font-family: Arial, Helvetica, sans-serif; color:#FFFFFF;");
        println("color:#FFFFFF;}");
        println("");

        print(".NavBarCell2    { font-family: Arial, Helvetica, sans-serif; ");
        println("background-color:#FFFFFF; color:#000000}");
        print(".NavBarCell3    { font-family: Arial, Helvetica, sans-serif; ");
        println("background-color:#FFFFFF; color:#000000}");
        println("");

    }

}
