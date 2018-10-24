package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteToDoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new DeleteToDoCommand object
 */
public class DeleteToDoCommandParser implements ParserToDo<DeleteToDoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteToDoCommand
     * and returns an DeleteToDoCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteToDoCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteToDoCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteToDoCommand.MESSAGE_USAGE), pe);
        }
    }

}
