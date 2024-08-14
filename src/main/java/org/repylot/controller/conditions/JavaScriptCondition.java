package org.repylot.controller.conditions;

public class JavaScriptCondition implements Condition {
    @Override
    public boolean satisfy(Object url) {
        if (url instanceof String)
            return isPythonFile((String) url);

        else throw new RuntimeException();
    }

    private boolean isPythonFile(String url) {
        int extensionBegin = url.length() - 3;
        return url.substring(extensionBegin).equals(".s");
    }
}
