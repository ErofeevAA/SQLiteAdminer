package utils;

/**
 * All array is sorted
 * */
public class Keywords {
    public static final String[] commandWords = {
            "ABORT", "ACTION", "ADD", "AFTER", "ALL", "ALTER", "ALWAYS", "ANALYZE", "AND", "AS", "ASC", "ATTACH",
            "AUTOINCREMENT", "BEFORE", "BEGIN", "BETWEEN", "BY", "CASCADE", "CASE", "CHECK", "COLLATE", "COLUMN",
            "COMMIT", "CONFLICT", "CONSTRAINT", "CREATE", "CROSS", "CURRENT", "DATABASE", "DEFAULT", "DEFERRABLE",
            "DEFERRED", "DELETE", "DESC", "DETACH", "DISTINCT", "DO", "DROP", "EACH", "ELSE", "END", "ESCAPE", "EXCEPT",
            "EXCLUSIVE", "EXISTS", "EXPLAIN", "FAIL", "FILTER", "FOLLOWING", "FOR", "FOREIGN", "FROM", "FULL",
            "GENERATED", "GLOB", "GROUP", "HAVING", "IF", "IGNORE", "IMMEDIATE", "IN", "INDEX", "INDEXED", "INITIALLY",
            "INNER", "INSERT", "INSTEAD", "INTERSECT", "INTO", "IS", "ISNULL", "JOIN", "KEY", "LEFT", "LIKE", "LIMIT",
            "MATCH", "NATURAL", "NO", "NOT", "NOTHING", "NOTNULL", "NULL", "OF", "OFFSET", "ON", "OR", "ORDER", "OUTER",
            "OVER", "PARTITION", "PLAN", "PRAGMA", "PRECEDING", "PRIMARY", "QUERY", "RAISE", "RANGE", "RECURSIVE",
            "REFERENCES", "REGEXP", "REINDEX", "RELEASE", "RENAME", "REPLACE", "RESTRICT", "RIGHT", "ROLLBACK", "ROW",
            "ROWS", "SAVEPOINT", "SELECT", "SET", "TABLE", "TEMP", "TEMPORARY", "THEN", "TO", "TRANSACTION", "TRIGGER",
            "UNBOUNDED", "UNION", "UNIQUE", "UPDATE", "USING", "VACUUM", "VALUES", "VIEW", "VIRTUAL", "WHEN", "WHERE",
            "WINDOW", "WITH", "WITHOUT",
    };

    public static final String[] typeDataWords = {
            "BLOB", "INTEGER", "REAL", "TEXT",
    };

    public static final String[]  funcWords = {
            "ABS(", "AVG(", "CAST(", "CHANGES(", "CHAR(", "COALESCE(", "COUNT(", "CURRENT_DATE(", "CURRENT_TIME(",
            "CURRENT_TIMESTAMP(", "DATE(", "DATETIME(", "GLOB(", "GROUP_CONCAT(", "HEX(", "IFNULL(", "IIF(", "INSTR(",
            "JULIANDAY(", "LAST_INSERT_ROWID(", "LENGTH(", "LIKE(", "LIKELY(", "LOAD_EXTENSION(", "LOWER(", "LTRIM(",
            "MAX(", "MIN(", "NULLIF(", "PRINTF(", "QUOTE(", "RANDOM(", "RANDOMBLOB(", "REPLACE(", "ROUND(", "RTRIM(",
            "SOUNDEX(", "SQLITE_COMPILEOPTION_GET(", "SQLITE_COMPILEOPTION_USED(", "SQLITE_OFFSET(", "SQLITE_SOURCE_ID(",
            "SQLITE_VERSION(", "STRFTIME(", "SUBSTR(", "SUM(", "TIME(", "TOTAL_CHANGED(", "TRIM(", "TYPEOF(", "UNICODE(",
            "UNLIKELY(", "UPPER(", "ZEROBLOB(",
    };
}