import React from "react";
import {styled} from "@mui/material/styles";
import {Box} from "@mui/material";
import Action from "./Action";
import Message from "./Message";
import PropTypes from "prop-types";

const styles = ({theme}) => ({
  cursor: 'pointer',
  width: '87.5%',
  display: 'flex',
  justifyContent: 'flex-end',
  alignItems: 'center',

  '& > .LeftMessage': {
    borderBottomLeftRadius: 4,
    backgroundColor: '#eff3f4',
  },

  '& > .RightMessage': {
    borderBottomRightRadius: 4,
    backgroundColor: '#1d9bf0',
  }
});

const BoxWrapper = styled(Box)(styles);

const MessageBox = ({left, text}) => {

  return (
    <BoxWrapper>
      {left ? (<>
        <Message left={left} text={text}/>
        <Action/>
      </>) : (<>
        <Action/>
        <Message text={text}/>
      </>)}
    </BoxWrapper>);
}

MessageBox.propTypes = {
  left: PropTypes.bool,
  text: PropTypes.string,
}

export default MessageBox;