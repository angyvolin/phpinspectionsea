package com.kalessil.phpStorm.phpInspectionsEA;

import com.intellij.testFramework.fixtures.CodeInsightFixtureTestCase;
import com.kalessil.phpStorm.phpInspectionsEA.inspectors.SecurityAdvisoriesInspector;

final public class SecurityAdvisoriesInspectorTest extends CodeInsightFixtureTestCase {
    public void testMissingAdvisory() {
        myFixture.configureByFile("fixtures/securityAdvisories/needsAdvisories/composer.json");
        myFixture.enableInspections(SecurityAdvisoriesInspector.class);
        myFixture.testHighlighting(true, false, true);
    }

    public void testNonMasterAdvisory() {
        myFixture.configureByFile("fixtures/securityAdvisories/needsMasterAdvisories/composer.json");
        myFixture.enableInspections(SecurityAdvisoriesInspector.class);
        myFixture.testHighlighting(true, false, true);
    }
}