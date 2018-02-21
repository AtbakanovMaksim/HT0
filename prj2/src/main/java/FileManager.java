import com.mpatric.mp3agic.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    final static String EXTENSION = "mp3";
    List<Mp3File> mp3List = new ArrayList<Mp3File>();
    List<Artist> artistList = new ArrayList<Artist>();


    public void findFiles(String path, String extension) {
        File folder = new File(path);
        if (!folder.exists()) {
            System.out.println("You have enter incorrect path");
        } else {
            File[] listOfFiles = folder.listFiles();

            if (listOfFiles != null) {
                for (File file : listOfFiles) {
                    if (file.isDirectory()) {
                        findFiles(path + "/" + file.getName(), extension);
                    }
                    if (file.getName().endsWith(extension)) {
                        try {
                            Mp3File mp3file = new Mp3File(file.getAbsolutePath());
                            mp3List.add(mp3file);
//                            System.out.println(file.getAbsolutePath());
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (UnsupportedTagException e) {
                            e.printStackTrace();
                        } catch (InvalidDataException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private void getSets() {
        for (int i = 0; i < mp3List.size(); i++) {
            if (mp3List.get(i).hasId3v2Tag()) {
                ID3v2 id3v2Tag = mp3List.get(i).getId3v2Tag();
                if (id3v2Tag.getArtist() == null || id3v2Tag.getArtist().equalsIgnoreCase("")) {
                    id3v2Tag.setArtist("Unknown Artist");
                }
                if (id3v2Tag.getAlbum() == null || id3v2Tag.getAlbum().equalsIgnoreCase("")) {
                    id3v2Tag.setAlbum("Unknown Album");
                }
                if (id3v2Tag.getTitle() == null || id3v2Tag.getTitle().equalsIgnoreCase("")) {
                    id3v2Tag.setTitle("Unknown Track");
                }

                if (artistList.size() == 0) {
                    artistList.add(new Artist(id3v2Tag.getArtist()));
                }

                for (int j = 0; j < artistList.size(); j++) {
                    if (id3v2Tag.getArtist().equalsIgnoreCase(artistList.get(j).getName())) {
                        artistList.get(j).albumSet.add(id3v2Tag.getAlbum());
                    } else {
                        artistList.add(new Artist(id3v2Tag.getArtist()));
                        artistList.get(j + 1).albumSet.add(id3v2Tag.getAlbum());
                    }
                }

            }
        }
    }

    public void printCatalog() throws IOException {
        getSets();
        String resultFileName = "result.html";
        FileWriter writer = new FileWriter(resultFileName, true);
        BufferedWriter bufferWriter = new BufferedWriter(writer);
        StringBuffer buffer = new StringBuffer();
        for (Artist artist : artistList) {
            buffer.append(artist.getName() + "<br>\n");
            for (String album : artist.albumSet) {
                buffer.append(album + "<br>\n");
                for (int i = 0; i < mp3List.size(); i++) {
                    if (artist.getName().equalsIgnoreCase(mp3List.get(i).getId3v2Tag().getArtist()) &&
                            album.equalsIgnoreCase(mp3List.get(i).getId3v2Tag().getAlbum())) {
                        buffer.append("\t" + mp3List.get(i).getId3v2Tag().getTitle() + " " +
                                ((int) mp3List.get(i).getLengthInSeconds() / 60 + ":" + (int) mp3List.get(i).getLengthInSeconds() % 60)
                                + " " + "(<a href=\"" + mp3List.get(i).getFilename() + "\">Ссылка)</a>" + "<br>\n");
                    }
                }
            }
            buffer.append("<br>\n");
        }
        bufferWriter.write(buffer.toString());
        bufferWriter.close();
    }

    public void searchDublicants() throws IOException {
        String resultFileName = "sameTags.txt";
        FileWriter writer = new FileWriter(resultFileName, true);
        BufferedWriter bufferWriter = new BufferedWriter(writer);
        Logger log = LogManager.getLogger(FileManager.class.getName());
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < mp3List.size()-1; i++) {
            if (mp3List.get(i).hasId3v2Tag()) {
                ID3v2 firstID3 = mp3List.get(i).getId3v2Tag();
            for (int j = i+1; j < mp3List.size(); j++) {
                if (mp3List.get(j).hasId3v2Tag()) {
                    ID3v2 secondID3 = mp3List.get(j).getId3v2Tag();
                    if ((firstID3.getTitle().equalsIgnoreCase(secondID3.getTitle())) &&
                            (firstID3.getAlbum().equalsIgnoreCase(secondID3.getAlbum())) &&
                            (firstID3.getArtist().equalsIgnoreCase(secondID3.getArtist()))) {
                        buffer.append(firstID3.getArtist() + ", " + firstID3.getAlbum() + ", " + firstID3.getTitle() + "\n");
                        buffer.append(mp3List.get(i).getFilename()+ "\n");
                        buffer.append(mp3List.get(j).getFilename()+ "\n");
                    }
                    }
                }
            }
        }
        log.info(buffer);
        bufferWriter.write(buffer.toString());
        bufferWriter.close();
    }
}
