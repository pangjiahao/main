package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CALENDAR_EVENTS;
import static seedu.address.testutil.TypicalEvents.BENSON;
import static seedu.address.testutil.TypicalEvents.LECTURE;

import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.calendarevent.TitleContainsKeywordsPredicate;
import seedu.address.testutil.AddressBookBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasCalendarEvent(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasCalendarEvent(LECTURE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addCalendarEvent(LECTURE);
        assertTrue(modelManager.hasCalendarEvent(LECTURE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredCalendarEventList().remove(0);
    }

    @Test
    public void equals() {
        Scheduler scheduler = new AddressBookBuilder().withPerson(LECTURE).withPerson(BENSON).build();
        Scheduler differentScheduler = new Scheduler();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(scheduler, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(scheduler, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different scheduler -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentScheduler, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = LECTURE.getTitle().value.split("\\s+");
        modelManager.updateFilteredCalendarEventList(new TitleContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(scheduler, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredCalendarEventList(PREDICATE_SHOW_ALL_CALENDAR_EVENTS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setSchedulerFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(scheduler, differentUserPrefs)));
    }
}