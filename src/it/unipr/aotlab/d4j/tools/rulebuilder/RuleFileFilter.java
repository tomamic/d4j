package it.unipr.aotlab.d4j.tools.rulebuilder;

import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.util.*;

public class RuleFileFilter extends FileFilter {

    private List filters = null;
    private String description = null;

    /**
     * Creates a file filter from the given string array and description.
     * Example: new ExampleFileFilter(String {"gif", "jpg"}, "Gif and JPG Images");
     *
     * Note that the "." before the extension is not needed and will be ignored.
     */
    public RuleFileFilter(String[] filters, String description) {

        this.filters = new ArrayList();

        for (int i = 0; i < filters.length; i++) {
            this.filters.add(filters[i].toLowerCase());
        }

        this.description = description;
    }

    /**
     * Return true if this file should be shown in the directory pane,
     * false if it shouldn't.
     *
     * Files that begin with "." are ignored.
     *
     */
    public boolean accept(File f) {

        if (f != null) {
            if (f.isDirectory()) {
                return true;
            }

            String extension = getExtension(f);

            if (extension != null && filters.contains(extension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return the extension portion of the file's name .
     *
     * @see #getExtension
     * @see FileFilter#accept
     */
    public String getExtension(File f) {

        if (f != null) {
            String filename = f.getName();
            int i = filename.lastIndexOf('.');
            if (i > 0 && i < filename.length() - 1) {
                return filename.substring(i).toLowerCase();
            }
            ;
        }

        return null;
    }

    /**
     * Returns the human readable description of this filter. For
     * example: "JPEG and GIF Image Files (*.jpg, *.gif)"
     **/
    public String getDescription() {

        StringBuffer buffer = new StringBuffer(" ( ");


        for (int x = 0; x < filters.size(); x++) {

            String extension = (String) filters.get(x);
            buffer.append("*").append(extension);

            if (x < (filters.size() - 1)) {
                buffer.append(", ");
            } else {
                buffer.append(" )");
            }
        }

        return description + buffer.toString();
    }

    public String getPrimaryExtension() {
        String primaryExtension = null;

        if (filters.size() >= 1) {
            primaryExtension = (String) filters.get(0);
        } else {
            primaryExtension = "";
        }

        return primaryExtension.trim();
    }

    public File getCompleteName(File name) {

        String primaryExtension = getPrimaryExtension();

        if (name.getName().toLowerCase().endsWith(primaryExtension)) {
            return name;
        } else {

            String path = name.getPath();

            return new File(path.trim() + primaryExtension.trim());
        }
    }

}
