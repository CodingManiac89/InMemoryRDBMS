package db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import constants.ColumnType;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import table.Column;
import table.Table;
import utils.Utils;

public class Database {
	private static Database instance=new Database();
	private Map<String,Table> tables=new HashMap<>();
	private Database() {
		
		
	}
	
	public static Database getInstance() {
		return instance;
	}

	public Map<String, Table> getTables() {
		return tables;
	}	
}
