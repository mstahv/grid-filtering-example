package org.example.views;

import com.vaadin.demo.domain.DataService;
import com.vaadin.demo.domain.Person;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route
public class GridColumnFiltering extends Div {

    Grid<Person> grid = new Grid<>(Person.class, false);
    private final TextField fullNameFilter;
    private final TextField emailFilter;
    private final TextField professionFilter;

    public GridColumnFiltering() {
        // tag::snippet1[]
        Grid.Column<Person> nameColumn = grid.addColumn(p -> p.getFirstName() + " " + p.getLastName())
                .setHeader("Name");
        grid.addColumns("email", "profession");

        grid.setItems(DataService.getPeople());

        HeaderRow hr = grid.appendHeaderRow();

        fullNameFilter = createSearchField("name",hr.getCell(nameColumn));
        emailFilter = createSearchField("email", hr.getCell(grid.getColumnByKey("email")));
        professionFilter = createSearchField("profession", hr.getCell(grid.getColumnByKey("profession")));
        // end::snippet1[]

        add(grid);
    }
    
    private TextField createSearchField(String labelText, HeaderRow.HeaderCell cell) {
        TextField textField = new TextField();
        textField.setPlaceholder("Filter by " + labelText + "...");
        textField.setValueChangeMode(ValueChangeMode.LAZY);
        textField.setClearButtonVisible(true);
        textField.setWidthFull();
        textField.addValueChangeListener(e -> filterItems());
        cell.setComponent(textField);
        return textField;
    }

    private void filterItems() {
        grid.setItems(DataService.getPeople().stream()
                .filter(this::matchesFilters)
                .toList());
    }

    public boolean matchesFilters(Person person) {
        boolean matchesFullName = matches(person.getFullName(), fullNameFilter.getValue());
        boolean matchesEmail = matches(person.getEmail(), emailFilter.getValue());
        boolean matchesProfession = matches(person.getProfession(),
                professionFilter.getValue());
        return matchesFullName && matchesEmail && matchesProfession;
    }
    private boolean matches(String value, String searchTerm) {
        return searchTerm == null || searchTerm.isEmpty()
                || value.toLowerCase().contains(searchTerm.toLowerCase());
    }

}
