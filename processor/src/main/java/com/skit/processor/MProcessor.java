package com.skit.processor;

import com.skit.annotation.SIGN;

import java.io.Writer;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.SimpleElementVisitor8;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

public class MProcessor extends AbstractProcessor {

    private Filer envFiler;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        envFiler = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        try {
            JavaFileObject signMapUtils = envFiler.createSourceFile("SignMapUtils");
            System.out.println(signMapUtils);
            Writer writer = signMapUtils.openWriter();
            writer.write("package com.skit.processor;\n");

            writer.write("import java.util.ArrayList;\n");
            writer.write("import java.util.HashMap;\n");
            writer.write("import java.util.List;\n");
            writer.write("import java.util.Map;\n");

            writer.write("public class SignMapUtils {\n");

            writer.write("\tpublic static Map<String, List<String>> map = new HashMap();\n");
            writer.write("\tstatic {\n");

            writer.write("\t\tList<String> list1 = new ArrayList<>();\n");
            Set<? extends Element> signElements = roundEnvironment.getElementsAnnotatedWith(SIGN.class);
            for (Element rootElement : signElements) {
                ElementKind kind = rootElement.getKind();
                if (kind.isClass()) {
                    Name className = rootElement.accept(new SimpleElementVisitor8<Name, Void>() {
                        @Override
                        public Name visitType(TypeElement typeElement, Void aVoid) {
                            return typeElement.getQualifiedName();
                        }
                    }, null);
                    writer.write("\t\tlist1.add(\"" + className + "\");\n");
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, className);
                }
            }
            writer.write("\t\tmap.put(\"" + SIGN.class.getSimpleName() + "\",list1);\n");

            writer.write("\t}\n");


            writer.write("}\n");
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> strings = new HashSet<>();
        strings.add(SIGN.class.getCanonicalName());
        return strings;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return processingEnv.getSourceVersion();
    }
}