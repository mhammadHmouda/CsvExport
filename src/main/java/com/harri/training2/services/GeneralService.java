package com.harri.training2.services;

import com.harri.training2.mappers.AutoMapper;
import com.harri.training2.models.CsvPlayer;
import com.harri.training2.models.ExportType;
import com.harri.training2.models.PlayerInfo;
import com.harri.training2.repositories.PlayersInfoRepository;
import com.harri.training2.utils.CsvUtils;
import com.harri.training2.utils.SSEUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GeneralService {

    private final PlayersInfoRepository playersInfoRepository;
    private final AutoMapper<PlayerInfo, CsvPlayer> mapper;
    private final S3FileUploadService s3Service;

    public Object exportCsv(ExportType exportType) {
        List<PlayerInfo> playersModel = playersInfoRepository.executeQuery();

        List<CsvPlayer> csvModels = playersModel.stream()
                .map(player -> mapper.toDto(player, CsvPlayer.class))
                .toList();

        List<?> results = CsvUtils.toCsv(csvModels);

        String s = results.stream().map(Object::toString).collect(Collectors.joining("\n"));

        if (exportType.equals(ExportType.CHUNK)){
            return s;
        }
        else if (exportType.equals(ExportType.STREAM)) {
            return SSEUtils.writeToSSE(results);
        }
        else if (exportType.equals(ExportType.S3)) {
            s3Service.uploadCsvToS3("csv-export-players", "csv-players", s);
            return "Csv players uploaded to the s3 bucket!";
        }

        return s;
    }
}