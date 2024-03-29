import { styled } from "@mui/material/styles";
import { Avatar, Box, Link } from "@mui/material";

export const TweetContainer = styled(Box)(({theme}) => ({
  borderRadius: 0,
  color: "rgb(83, 100, 113)",
  textDecoration: "none",
  cursor: "pointer",
  flex: "unset",
  position: "relative",
  padding: "1rem",

  borderTop: `1px solid ${theme.palette.border.main}`,
  maxWidth: 600,
  ["@media (min-width:700px)"]: {
    flex: 7,
  },
}));

export const Content = styled(Box)({
  display: "flex",
  justifyContent: "space-between",
});
export const AvatarWrapper = styled(Box)({
  display: "flex",
  flexDirection: "column",
});
export const UserAvatar = styled(Avatar)({
  width: 43,
  height: 43,
  marginBottom: 5,
  "&:hover": {
    opacity: 0.8,
  },
});
export const AvatarDecorate = styled(Box)({
  display: "inline-block",
  width: 1,
  background: "rgb(207, 217, 222)",
  margin: "0 auto",
  height: "100%",
});
export const PostInfo = styled(Box)({
  display: "flex",
  flexWrap: "wrap",
  alignItems: "center",
  marginBottom: 0.125,
});
export const UserName = styled(Link)(({theme}) => ({
  fontSize: "inherit",
  fontWeight: 700,
  color: theme.palette.textColor,
}));
export const IconBlue = styled(Box)({
  "&:hover svg": {
    backgroundColor: "lightcyan",
    color: "rgb(29, 155, 240)",
    borderRadius: "50%",
  },
  "&:hover span": {
    color: "rgb(29, 155, 240)",
  },
});
