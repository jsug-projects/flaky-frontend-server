package jsug.flaky;

import java.io.Serializable;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FlakyUser implements Serializable {
	private final UUID memberId;
	private final String firstName;
	private final String lastName;

	@JsonCreator
	public FlakyUser(@JsonProperty("memberId") UUID memberId,
                     @JsonProperty("firstName") String firstName,
                     @JsonProperty("lastName") String lastName) {
		this.memberId = memberId;
		this.firstName = firstName;
		this.lastName = lastName;
	}
}
