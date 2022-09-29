package sub;

public enum CommandsEnum {
    HELP("help"),
    SHOW("show"),
    ADD("add"),
    UPDATE("update"),
    REMOVE_BY_ID("remove_by_id"),
    CLEAR("clear"),
    EXECUTE_SCRIPT("execute_script"),
    EXIT("exit"),
    INSERT_AT("insert_at"),
    ADD_IF_MIN("add_if_min"),
    SHUFFLE("shuffle"),
    REMOVE_ALL_BY_SCREENWRITER("remove_all_by_screenwriter"),
    GROUP_COUNTING_BY_TAGLINE("group_counting_by_tagline"),
    PRINT_DESCENDING("print_descending"),
    INFO("info"),
    AUTHORIZATION("authorization");

    public String commandName;

    CommandsEnum(String commandName) {
        this.commandName = commandName;
    }
}
