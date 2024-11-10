# DOM SAX hybrid parser

the parser is design for UCI CS122B project 3 task 5

## Running the program

```sh
javac Cast.java CastMasterSAXParser.java CastWorkerDOMParser.java InsertCastBatch.java InsertMovieBatch.java InsertStarBatch.java Main.java Movie.java MovieMasterSAXParser.java MovieWorkerDOMParser.java Star.java StarMasterSAXParser.java StarWorkerDOMParser.java && java Main 2>error.log && cat sql_commands_start.sql movie_sql_commands.sql star_sql_commands.sql commit_sql_command.sql cast_sql_commands.sql commit_sql_command.sql sql_commands_end.sql | sudo mysql -u mytestuser -p 2>>error.log
```
