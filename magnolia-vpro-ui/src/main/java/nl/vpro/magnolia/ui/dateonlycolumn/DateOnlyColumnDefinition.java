package nl.vpro.magnolia.ui.dateonlycolumn;

import info.magnolia.ui.contentapp.configuration.column.ColumnType;
import info.magnolia.ui.contentapp.configuration.column.DateColumnDefinition;

/**
 * Justs overrides the {@link #getRenderer()} to display only the date part of  {@link java.util.Date}.
 * @author Michiel Meeuwissen
 * @since 1.0
 */
@ColumnType("dateOnlyColumn")
public class DateOnlyColumnDefinition extends DateColumnDefinition {


     public DateOnlyColumnDefinition() {
         super();
         setRenderer(DateOnlyRenderer.class);
     }
}
