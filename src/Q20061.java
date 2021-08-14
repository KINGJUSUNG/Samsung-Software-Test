import java.io.*;
import java.util.*;

public class Q20061 {
    static int N;
    static int score = 0;
    static int[][] green = new int[6][4];
    static int[][] blue = new int[4][6];
    static int[] green_h = {0, 0, 0, 0};
    static int[] blue_h = {0, 0, 0, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            int t = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            green_process(t, y);
            blue_process(t, x);
        }

        int cnt = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                if (green[i][j] == 1) {
                    cnt++;
                }
                if (blue[j][i] == 1) {
                    cnt++;
                }
            }
        }

        System.out.println(score);
        System.out.print(cnt);
    }

    static void blue_process(int t, int x) {

        if (t == 1) {
            int h = blue_h[x];
            blue[x][5 - h] = 1;
            blue_h[x] = h + 1;
        }

        if (t == 2) {
            int h = blue_h[x];
            blue[x][5 - h] = 1;
            blue[x][(5 - h) - 1] = 1;
            blue_h[x] = h + 2;
        }

        if (t == 3) {
            int h1 = blue_h[x], h2 = blue_h[x + 1];
            int h = Math.max(h1, h2);
            blue[x][5 - h] = 1;
            blue[x + 1][5 - h] = 1;
            blue_h[x] = h + 1;
            blue_h[x + 1] = h + 1;
        }

        for (int i = 5; i >= 0; i--) {
            boolean isRemove = true;
            for (int j = 0; j < 4; j++) {
                if (blue[j][i] == 0) {
                    isRemove = false;
                    break;
                }
            }

            // i 열 삭제
            if (isRemove) {
                score++;
                for (int j = 0; j < 4; j++) {
                    int h = blue_h[j];
                    blue[j][i] = 0;
                    blue_h[j] = 0;

                    // blue 배열 재설정
                    for (int k = i - 1; k >= 6 - h; k--) {
                        blue[j][k + 1] = blue[j][k];
                        blue[j][k] = 0;
                    }

                    // blue_h 배열 재설정
                    for (int k = 0; k <= 5; k++) {
                        if (blue[j][k] == 1) {
                            blue_h[j] = 6 - k;
                            break;
                        }
                    }
                }
            }
        }

        // 0, 1 번 열에 블록이 있는 경우
        int cnt = 0;
        for (int i = 0; i <= 1; i++) {
            for (int j = 0; j < 4; j++) {
                if (blue[j][i] == 1) {
                    cnt++;
                    break;
                }
            }
        }

        if (cnt > 0) {
            for (int i = 5 - cnt; i >= 0; i--) {
                for (int j = 0; j < 4; j++) {
                    blue[j][i + cnt] = blue[j][i];
                    blue[j][i] = 0;
                }
            }
            for (int i = 0; i < 4; i++) {
                blue_h[i] = blue_h[i] <= cnt ? 0 : blue_h[i] - cnt;
            }
        }
    }

    static void green_process(int t, int y) {

        if (t == 1) {
            int h = green_h[y];
            green[5 - h][y] = 1;
            green_h[y] = h + 1;
        }

        if (t == 2) {
            int h1 = green_h[y], h2 = green_h[y + 1];
            int h = Math.max(h1, h2);
            green[5 - h][y] = 1;
            green[5 - h][y + 1] = 1;
            green_h[y] = h + 1;
            green_h[y + 1] = h + 1;
        }

        if (t == 3) {
            int h = green_h[y];
            green[5 - h][y] = 1;
            green[(5 - h) - 1][y] = 1;
            green_h[y] = h + 2;
        }

        for (int i = 5; i >= 0; i--) {
            boolean isRemove = true;
            for (int j = 0; j < 4; j++) {
               if (green[i][j] == 0) {
                   isRemove = false;
                   break;
               }
            }

            // i 행 삭제
            if (isRemove) {
                score++;
                for (int j = 0; j < 4; j++) {
                    int h = green_h[j];
                    green[i][j] = 0;
                    green_h[j] = 0;

                    // green 배열 재설정
                    for (int k = i - 1; k >= 6 - h; k--) {
                        green[k + 1][j] = green[k][j];
                        green[k][j] = 0;
                    }

                    // green_h 배열 재설정
                    for (int k = 0; k <= 5; k++) {
                        if (green[k][j] == 1) {
                            green_h[j] = 6 - k;
                            break;
                        }
                    }
                }
            }
        }

        // 0, 1 번 행에 블록이 있는 경우
        int cnt = 0;
        for (int i = 0; i <= 1; i++) {
            for (int j = 0; j < 4; j++) {
                if (green[i][j] == 1) {
                    cnt++;
                    break;
                }
            }
        }

        if (cnt > 0) {
            for (int i = 5 - cnt; i >= 0; i--) {
                for (int j = 0; j < 4; j++) {
                    green[i + cnt][j] = green[i][j];
                    green[i][j] = 0;
                }
            }
            for (int i = 0; i < 4; i++) {
                green_h[i] = green_h[i] <= cnt ? 0 : green_h[i] - cnt;
            }
        }
    }

    static void print(int[][] arr) {
        for (int[] ints : arr) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            } System.out.println();
        } System.out.println();
    }
}

/*
        9
        2 1 0
        2 1 0
        2 1 0
        2 1 0
        2 1 0
        3 0 2
        3 0 2
        3 0 3
        3 0 3
 */
