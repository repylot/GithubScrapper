package org.repylot.controller.conditions;

public class JavaCondition implements Condition {
    @Override
    public boolean satisfy(Object url) {
        if (url instanceof String)
            return isPythonFile((String) url);

        else throw new RuntimeException();
    }

    private boolean isPythonFile(String url) {
        int extensionBegin = url.length() - 5;
        return url.substring(extensionBegin).equals(".java");
    }
}
