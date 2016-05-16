package com.foya.apache.metamodel;

import java.io.File;
import java.util.List;

import javax.sql.DataSource;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.UpdateCallback;
import org.apache.metamodel.UpdateScript;
import org.apache.metamodel.UpdateableDataContext;
import org.apache.metamodel.csv.CsvDataContext;
import org.apache.metamodel.data.DataSet;
import org.apache.metamodel.data.Row;
import org.apache.metamodel.excel.ExcelDataContext;
import org.apache.metamodel.insert.RowInsertionBuilder;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.query.builder.InitFromBuilder;
import org.apache.metamodel.query.builder.SatisfiedSelectBuilder;
import org.apache.metamodel.query.builder.TableFromBuilder;
import org.apache.metamodel.schema.Column;
import org.apache.metamodel.schema.Schema;
import org.apache.metamodel.schema.Table;
import org.apache.metamodel.update.RowUpdationBuilder;
import org.apache.metamodel.xml.XmlDomDataContext;
import org.junit.Before;
import org.junit.Test;

public class ReadExcel01 {

	private String BASE_URL;
	private String FOLDER;
	private ExcelDataContext updateableDataContext;

	@Before
	public void before() {
		BASE_URL = System.getProperty("user.dir");
		FOLDER = BASE_URL + "/src/main/resources/metamodel";
		updateableDataContext = new ExcelDataContext(new File(FOLDER + "/123.xls"));
		System.out.println(updateableDataContext);
		UpdateableDataContext csv = new CsvDataContext(new File("data.csv"));
		System.out.println(csv);

//		DataSource dataSource = null;
//		UpdateableDataContext jdbc = new JdbcDataContext(dataSource);
//		System.out.println(jdbc);

		DataContext xml = new XmlDomDataContext(new File("data.xml"));
	}

	@Test
	public void readExcel01() {

		Schema schema = updateableDataContext.getDefaultSchema();
		System.out.println(schema);
		Table table = schema.getTables()[0];
		Column amountColumn = table.getColumnByName("Amount");
		Column accountColumn = table.getColumnByName("Account");
		System.out.println(amountColumn);
		System.out.println(accountColumn);

	}

	@Test
	public void insertExcel() {
		final Table table = updateableDataContext.getDefaultSchema().getTable(0);
		updateableDataContext.executeUpdate(new UpdateScript() {
			@Override
			public void run(UpdateCallback callback) {
				System.out.println(callback.isUpdateSupported());
				RowInsertionBuilder insertInto = callback.insertInto(table);
				insertInto.value(0, "tommy11");
				insertInto.value(1, "tommy12");
				insertInto.execute();
			}
		});
	}

	@Test
	public void readExcel02() {
		Table table = updateableDataContext.getDefaultSchema().getTable(0);
		System.out.println(table);

		System.out.println("===================================");
		InitFromBuilder query = updateableDataContext.query();
		TableFromBuilder from = query.from(table);

		{

			SatisfiedSelectBuilder<?> select = from.select(new String[] { "Amount", "Account" });
			DataSet ds = select.execute();
			List<Row> rows = ds.toRows();
			for (Row row : rows) {
				System.out.println(row);
			}
		}
		System.out.println("===================================");
		{
			DataSet ds = from.selectAll().execute();
			List<Row> rows = ds.toRows();
			for (Row row : rows) {
				System.out.println(row);
			}
		}

	}

	@Test
	public void updateExcel() {
		final Table table = updateableDataContext.getDefaultSchema().getTable(0);
		updateableDataContext.executeUpdate(new UpdateScript() {
			@Override
			public void run(UpdateCallback callback) {
				// UPDATE table
				callback.update(table).value("Amount", "tommyQQQ").where("Account").eq("Account").execute();

			}
		});
	}

	@Test
	public void updateExcel02() {
		final Table table = updateableDataContext.getDefaultSchema().getTable(0);
		updateableDataContext.executeUpdate(new UpdateScript() {
			@Override
			public void run(UpdateCallback callback) {
				// UPDATE table
				RowUpdationBuilder rub = callback.update(table);
				rub.where("Amount").eq("tommy11");
				rub.value("Amount", "tommyQQQQQQ");
				rub.execute();

			}
		});

		updateableDataContext.executeUpdate(new UpdateScript() {
			@Override
			public void run(UpdateCallback callback) {
				// UPDATE table
				RowUpdationBuilder rub = callback.update(table);
				rub.where("Amount").eq("tommyQQQQQQ");
				rub.value("Amount", "tommy11");
				rub.execute();

			}
		});

		updateableDataContext.executeUpdate(new UpdateScript() {
			@Override
			public void run(UpdateCallback callback) {

				callback.deleteFrom(table).where("Amount").isNull().execute();
				//.like("%Piggy").execute();

			}
		});

	}

	public void demo() {
		BASE_URL = System.getProperty("user.dir");
		FOLDER = BASE_URL + "/src/main/resources/metamodel";
		updateableDataContext = new ExcelDataContext(new File(FOLDER + "/123.xls"));

		UpdateableDataContext csv = new CsvDataContext(new File("data.csv"));

		DataSource dataSource = null;
		UpdateableDataContext jdbc = new JdbcDataContext(dataSource);

		DataContext xml = new XmlDomDataContext(new File("data.xml"));
	}
}
