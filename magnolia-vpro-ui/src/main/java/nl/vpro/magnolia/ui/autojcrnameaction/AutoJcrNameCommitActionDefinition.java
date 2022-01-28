package nl.vpro.magnolia.ui.autojcrnameaction;

import info.magnolia.ui.api.action.ActionType;
import info.magnolia.ui.contentapp.action.CommitActionDefinition;
import lombok.Getter;
import lombok.Setter;

@ActionType("autoJcrCommitAction")
public class AutoJcrNameCommitActionDefinition extends CommitActionDefinition {


    @Getter
    @Setter
    private String propertyName = "title";

      public AutoJcrNameCommitActionDefinition() {
        setImplementationClass(AutoJcrNameCommitAction.class);
    }
}
