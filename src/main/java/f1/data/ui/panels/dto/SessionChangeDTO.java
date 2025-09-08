package f1.data.ui.panels.dto;

import f1.data.parse.packets.ParticipantData;

import java.util.List;

public record SessionChangeDTO(int playerDriverId, int teamMateDriverId, int numActiveCars, List<ParticipantData> participantData) {
}
