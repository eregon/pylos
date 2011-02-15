package pylos.exception;

// TODO: This class should not exist.
// But it can be handy when one do not want yet to manage the exception.
public class PylosError extends Error {
	public PylosError(String message) {
		super(message);
	}
}
