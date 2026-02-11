	package finalAssesment3;
	
	import org.junit.jupiter.api.Test;
	import org.junit.jupiter.api.BeforeEach;
	import static org.junit.jupiter.api.Assertions.*;
	/**
	 * JUnit test class for Name.java
	 * Tests the Name class functionality including full name generation and initials.
	 */
	public class NameTest {
	    
	    private Name name2Parts;
	    
	    @BeforeEach
	    public void setUp() {
	        name2Parts = new Name("John", "Smith");
	    }
	    
	    @Test
	    public void testGetFullNameWithTwoNames() {
	        assertEquals("John Smith", name2Parts.getFullName());
	    }
	    
	    @Test
	    public void testGetInitialsWithTwoNames() {
	        assertEquals("JS", name2Parts.getInitials());
	    }
	    
	    
	    @Test
	    public void testInitialsAreUpperCase() {
	        Name lowercase = new Name("john", "smith");
	        assertEquals("JS", lowercase.getInitials());
	    }
	    
	}