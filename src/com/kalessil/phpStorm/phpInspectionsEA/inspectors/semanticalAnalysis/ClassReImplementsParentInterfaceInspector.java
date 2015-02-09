package com.kalessil.phpStorm.phpInspectionsEA.inspectors.semanticalAnalysis;

import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElementVisitor;
import com.jetbrains.php.lang.psi.elements.ClassReference;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.kalessil.phpStorm.phpInspectionsEA.openApi.BasePhpElementVisitor;
import com.kalessil.phpStorm.phpInspectionsEA.openApi.BasePhpInspection;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ClassReImplementsParentInterfaceInspector extends BasePhpInspection {
    private static final String strProblemDescription = "Interface %s% is already defined in a parent class.";

    @Override
    public PsiElementVisitor buildVisitor(@NotNull final ProblemsHolder holder, boolean isOnTheFly) {
        return new BasePhpElementVisitor() {
            public void visitPhpClass(PhpClass clazz) {
                /** check if parent class and own implements are available */
                List<ClassReference> listImplements = clazz.getImplementsList().getReferenceElements();
                if (null == listImplements || listImplements.size() == 0) {
                    return;
                }
                PhpClass objParentClass = clazz.getSuperClass();
                if (null == objParentClass || null == clazz.getNameIdentifier()) {
                    return;
                }

                for (PhpClass parentInterface : objParentClass.getImplementedInterfaces()) {
                    String parentInterfaceFQN = parentInterface.getFQN();
                    if (null == parentInterfaceFQN) {
                        continue;
                    }

                    for (ClassReference ownInterface : listImplements) {
                        String ownInterfaceFQN = ownInterface.getFQN();
                        if (null == ownInterfaceFQN) {
                            continue;
                        }

                        if (ownInterfaceFQN.equals(parentInterfaceFQN)) {
                            String strWarning = strProblemDescription.replace("%s%", ownInterfaceFQN);
                            holder.registerProblem(clazz.getNameIdentifier(), strWarning, ProblemHighlightType.WEAK_WARNING);
                            break;
                        }
                    }
                }
            }
        };
    }
}