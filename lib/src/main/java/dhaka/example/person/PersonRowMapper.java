package dhaka.example.person;

import dhaka.jast.Row;
import dhaka.jast.RowMapper;

class PersonRowMapper implements RowMapper<Person> {
    @Override
    public Person map(Row row) {
        var person = new Person();
        person.setId(row.getString("id"));
        person.setName(row.getString("name"));
        person.setDp(row.getString("dp"));
        person.setCreated(row.getLong("created"));
        person.setDeleted(row.getBoolean("deleted"));
        return person;
    }
}
